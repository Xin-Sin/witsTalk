/**
 * @class Render
 * 渲染器主类 用于渲染页面背景
 *
 * Copyright 2021-08-02 MrKBear mrkbear@qq.com
 * All rights reserved.
 * This file is part of the App project.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
import * as M from "gl-matrix";
import Shader from './Shader'
import Header from './Header'

export default class Render {

    /**
     * 绑定画布
     * @type {HTMLCanvasElement}
     */
    canvas: HTMLCanvasElement;

    /**
     * webgl上下文
     * @type {WebGL2RenderingContext|WebGLRenderingContext}
     */
    gl: WebGL2RenderingContext | WebGLRenderingContext;
    glVersion = 0;
    /**
     * 是否完成一次自适应
     * @type {boolean}
     */
    isResize = true;
    isMouseMove = false;
    mouseX = 0;
    mouseY = 0;
    /**
     * 视图矩阵
     * @type {mat4}
     */
    viewMat = M.mat4.create();
    /**
     * 缩放
     * @type {Number}
     */
    zoom = 2 * (2 / 3) ** .5;
    /**
     * 缩放矩阵
     * @type {mat4}
     */
    zoomMat = M.mat4.create();
    /**
     * 变换矩阵
     * @type {mat4}
     */
    mvpMat = M.mat4.create();
    /**
     * @type {Shader}
     */
        // @ts-ignore
    headerDrawProgram: Shader;
    /**
     * @type {WebGLBuffer}
     */
        // @ts-ignore
    headVecBuffer: WebGLBuffer;
    /**
     * 头颅列表
     * @type {Header[]}
     */
    headers: Header[] = [];

    /**
     * @param {HTMLCanvasElement} canvas
     */
    constructor(canvas: HTMLCanvasElement) {

        // 保存画布节点
        this.canvas = canvas;

        // 尝试 webgl2
        this.gl = this.canvas.getContext("webgl2") as WebGL2RenderingContext;
        if (this.gl) {
            this.glVersion = 2;
        } else {
            this.gl = this.canvas.getContext("webgl") as WebGLRenderingContext;
            if (this.gl) {
                this.glVersion = 1;
            }
        }

        // 获取上下文
        this.gl = this.canvas.getContext("webgl2") as WebGL2RenderingContext || this.canvas.getContext("webgl") as WebGLRenderingContext;

        // 开启深度测试
        this.gl.enable(this.gl.DEPTH_TEST);
        this.gl.enable(this.gl.CULL_FACE);

        // 计算观察者矩阵
        this.generateViewMat();

        // 自适应一次
        this.resize();

        // 初始化顶点绘图程序
        this.initHeaderDrawProgram();

        // 上传顶点数据
        this.initHeadData();
    }

    /**
     * 属性代理
     * @type {Number}
     */
    get width() {
        return this.canvas.width
    }

    get height() {
        return this.canvas.height
    }

    get offsetWidth() {
        return this.canvas.offsetWidth
    }

    get offsetHeight() {
        return this.canvas.offsetHeight
    }

    /**
     * 缩放
     * @type {Number}
     */
    get scaleX() {
        return this.width / this.offsetWidth
    }

    get scaleY() {
        return this.height / this.offsetHeight
    }

    /**
     * 分辨率 (画布宽高比)
     * @type Number
     */
    get ratio() {
        return this.canvas.offsetWidth / this.canvas.offsetHeight;
    }

    /**
     * 缩放比例
     */
    get unitZoom() {
        return 2 / (Math.floor(this.headers.length / 2) * 3 +
            (this.headers.length % 2 === 0 ? .5 : 2)
        );
    }

    /**
     * 单位 X 坐标
     */
    get unitX() {
        return 3 ** .5 * this.unitZoom / this.ratio;
    }

    /**
     * 单位 Y 坐标
     */
    get unitY() {
        return 2 * 3 * this.unitZoom / 4;
    }

    /**
     * 更新
     */
    update(t: number) {

        // 自适应从新计算布局
        if (this.isResize) {
            this.isResize = false;

            // 更新视图
            this.gl.viewport(0, 0, this.width, this.height);

            // 计算缩放矩阵
            this.generateZoomMat();

            // 计算变换矩阵
            this.generateMvpMat();

            // 计算绘制矩阵
            this.updateDrawArray();
        }

        // 检测鼠标更新
        if (this.isMouseMove) {
            this.isMouseMove = false;

            // 传递鼠标事件
            this.testRotate();
        }

        // 更新全部头颅
        for (let i = 0; i < this.headers.length; i++) {
            this.headers[i].update(t);
        }
    }

    /**
     * 上传贴图
     */
    updateTex() {
        for (let i = 0; i < this.headers.length; i++) {
            this.headers[i].updateImg(this.glVersion);
        }
    }

    /**
     * 画布分辨率自适应
     */
    resize() {
        this.isResize = true;
        this.canvas.width = window.innerWidth;
        this.canvas.height = window.innerHeight;
    }

    /**
     * 鼠标移动监听
     */
    mouseMove(x: number, y: number) {
        this.mouseX = x;
        this.mouseY = y;
        this.isMouseMove = true;
    }

    /**
     * 传递鼠标移动事件
     */
    testRotate() {

        let x = this.mouseX / this.offsetWidth;
        let y = 1 - this.mouseY / this.offsetHeight;

        x -= .5;
        x *= 2;
        y -= .5;
        y *= 2;

        // 保存虚拟变量值，提高性能
        let ux = this.unitX;
        let zoom = this.unitZoom;
        let ratio = this.ratio;
        let r = zoom * 3 ** .5 / (2 * ratio);

        // 传递事件
        for (let i = 0; i < this.headers.length; i++) {

            // 清除击中状态
            this.headers[i].emitIndex = -1;

            // 按 Y 轴初步检测
            let disY = Math.abs(this.headers[i].drawY - y);

            // Y 轴测试
            if (disY < zoom) this.headers[i].rotate(x, zoom, r, disY, ratio);
        }
    }

    /**
     * 生成视图矩阵
     * */
    generateViewMat() {

        // 视点
        const eye = M.vec3.create();
        M.vec3.set(eye, 1, 1, 1);

        // 目标
        const target = M.vec3.create();
        M.vec3.set(target, 0, 0, 0);

        // 旋转方向
        const up = M.vec3.create();
        M.vec3.set(up, 0, 1, 0);

        // 生成视图矩阵
        M.mat4.lookAt(this.viewMat, eye, target, up);
    }

    /**
     * 生成缩放矩阵
     */
    generateZoomMat() {

        // 根据头颅个数缩放
        let zoom = this.zoom / this.unitZoom;

        // 更新平行投影矩阵
        M.mat4.ortho(this.zoomMat,
            -zoom * this.ratio, zoom * this.ratio,
            -zoom, zoom,
            0, 10
        );
    }

    /**
     * 生成最终矩阵
     */
    generateMvpMat() {
        M.mat4.multiply(this.mvpMat, this.zoomMat, this.viewMat);
    }

    /**
     * 清除缓冲区
     */
    clean() {
        this.gl.clearColor(150 / 255, 198 / 255, 217 / 255, 1.);
        this.gl.clear(this.gl.COLOR_BUFFER_BIT | this.gl.DEPTH_BUFFER_BIT);
    }

    /**
     * 初始化头绘制程序
     */
    initHeaderDrawProgram() {
        const V = `
        attribute vec3 vPos;
        attribute vec2 vUv;
        attribute float vFront;
        attribute float vLeft;
        attribute float vRight;
        attribute float vTop;
        
        uniform mat4 mvp;
        uniform float rotate;
        uniform vec2 mp;
        
        varying vec2 uv;
        varying float front;
        varying float left;
        varying float right;
        varying float top;
        
        const float PI = 3.14159265;
        
        vec3 rotateY(vec3 p, float ang) {
            mat3 rMat = mat3(
                cos(ang),  0.,   sin(ang),
                0.,        1.,   0.,
                -sin(ang), 0.,   cos(ang)
            );
            return rMat * p;
        }
    
        void main(){
            vec4 rPos = vec4(rotateY(vPos, -PI/2. * rotate), 1.);
            rPos = mvp * rPos;
            rPos.x = rPos.x + mp.x;
            rPos.y = rPos.y + mp.y;
            gl_Position = rPos; uv = vUv;
            front = vFront; left = vLeft;
            right = vRight; top = vTop;
        }
        `;
        const F = `
        precision lowp float;
        
        varying vec2 uv;
        varying float front;
        varying float left;
        varying float right;
        varying float top;
        
        uniform sampler2D tFront;
        uniform sampler2D tLeft;
        uniform sampler2D tRight;
        uniform sampler2D tTop;
    
        void main(){
            vec4 color;
            color = texture2D(tFront, vec2(uv.x, -uv.y)) * front;
            color += texture2D(tLeft, vec2(uv.x, -uv.y)) * left;
            color += texture2D(tRight, vec2(uv.x, -uv.y)) * right;
            color += texture2D(tTop, vec2(uv.x, -uv.y)) * top;
            gl_FragColor = color;
        }
        `;

        // 创建着色器
        this.headerDrawProgram = new Shader(this.gl, V, F);

        // 获取顶点attr
        this.headerDrawProgram.attribLocation("vPos");
        this.headerDrawProgram.attribLocation("vUv");

        // 贴图向量
        this.headerDrawProgram.attribLocation("vFront");
        this.headerDrawProgram.attribLocation("vLeft");
        this.headerDrawProgram.attribLocation("vRight");
        this.headerDrawProgram.attribLocation("vTop");

        this.headerDrawProgram.uniformLocation("tFront");
        this.headerDrawProgram.uniformLocation("tLeft");
        this.headerDrawProgram.uniformLocation("tRight");
        this.headerDrawProgram.uniformLocation("tTop");

        this.headerDrawProgram.uniformLocation("mvp");
        this.headerDrawProgram.uniformLocation("rotate");
        this.headerDrawProgram.uniformLocation("mp");
    }

    /**
     * 初始化立方体数据
     */
    initHeadData() {

        const CUBE_TRIANGLE_DATA = new Float32Array([

            // 顶面
            1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, -1, 1, 1, 1, 0, 0, 0, -1, 1, -1, 0, 1, 1, 0, 0, 0,
            1, 1, 1, 1, 0, 1, 0, 0, 0, -1, 1, -1, 0, 1, 1, 0, 0, 0, -1, 1, 1, 0, 0, 1, 0, 0, 0,

            // 正面
            1, 1, 1, 1, 1, 0, 1, 0, 0, -1, -1, 1, 0, 0, 0, 1, 0, 0, 1, -1, 1, 1, 0, 0, 1, 0, 0,
            1, 1, 1, 1, 1, 0, 1, 0, 0, -1, 1, 1, 0, 1, 0, 1, 0, 0, -1, -1, 1, 0, 0, 0, 1, 0, 0,

            // 右侧
            1, -1, 1, 0, 0, 0, 0, 1, 0, 1, -1, -1, 1, 0, 0, 0, 1, 0, 1, 1, -1, 1, 1, 0, 0, 1, 0,
            1, -1, 1, 0, 0, 0, 0, 1, 0, 1, 1, -1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0,

            // 左侧
            -1, -1, 1, 1, 0, 0, 0, 0, 1, -1, 1, 1, 1, 1, 0, 0, 0, 1, -1, 1, -1, 0, 1, 0, 0, 0, 1,
            -1, -1, 1, 1, 0, 0, 0, 0, 1, -1, 1, -1, 0, 1, 0, 0, 0, 1, -1, -1, -1, 0, 0, 0, 0, 0, 1

        ]);

        // 创建缓冲区
        this.headVecBuffer = this.gl.createBuffer() as WebGLBuffer;

        // 上传数据
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.headVecBuffer);
        this.gl.bufferData(this.gl.ARRAY_BUFFER, CUBE_TRIANGLE_DATA, this.gl.STATIC_DRAW);
    }

    /**
     * 添加头颅
     */
    addHeader(list: string[]) {
        for (let i = 0; i < list.length; i++) {
            this.headers.push(new Header(this.gl).setSrcByUrl(list[i]))
        }
    }

    /**
     * 加载头颅
     * @return {Promise<Promise[]>}
     */
    loadHeader() {
        return Promise.all(this.headers.map((v) => v.loadImg()));
    }

    /**
     * 更新绘制数组
     */
    updateDrawArray() {

        // 保存虚拟变量值，提高性能
        let ux = this.unitX;
        let uy = this.unitY;

        // 计算最大绘制高度
        let top = this.headers.length % 2 === 0 ?
            this.headers.length / 2 - .5 :
            (this.headers.length - 1) / 2;

        // 计算
        for (let i = 0; i < this.headers.length; i++) {
            this.headers[i].setDrawArray(
                i % 2 === 1 ? ux / 2 : 0,
                (top - i) * uy, ux
            );
        }
    }

    /**
     * 绘制全部头颅
     */
    drawAllHeader() {

        // 使用程序
        this.headerDrawProgram.use();

        // 绑定缓冲区
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.headVecBuffer);

        // 启动指针传递
        this.gl.enableVertexAttribArray(this.headerDrawProgram.vPos);
        this.gl.enableVertexAttribArray(this.headerDrawProgram.vUv);
        this.gl.enableVertexAttribArray(this.headerDrawProgram.vFront);
        this.gl.enableVertexAttribArray(this.headerDrawProgram.vLeft);
        this.gl.enableVertexAttribArray(this.headerDrawProgram.vRight);
        this.gl.enableVertexAttribArray(this.headerDrawProgram.vTop);

        // 指定指针数据
        this.gl.vertexAttribPointer(this.headerDrawProgram.vPos,
            3, this.gl.FLOAT, false, 9 * 4, 0);
        this.gl.vertexAttribPointer(this.headerDrawProgram.vUv,
            2, this.gl.FLOAT, false, 9 * 4, 3 * 4);

        // 贴图向量
        this.gl.vertexAttribPointer(this.headerDrawProgram.vTop,
            1, this.gl.FLOAT, false, 9 * 4, 5 * 4);
        this.gl.vertexAttribPointer(this.headerDrawProgram.vFront,
            1, this.gl.FLOAT, false, 9 * 4, 6 * 4);
        this.gl.vertexAttribPointer(this.headerDrawProgram.vRight,
            1, this.gl.FLOAT, false, 9 * 4, 7 * 4);
        this.gl.vertexAttribPointer(this.headerDrawProgram.vLeft,
            1, this.gl.FLOAT, false, 9 * 4, 8 * 4);

        // 传递 mvp 矩阵
        this.gl.uniformMatrix4fv(this.headerDrawProgram.mvp, false, this.mvpMat);

        // 绘制全部方块
        for (let i = 0; i < this.headers.length; i++) {

            // 设置贴图
            this.gl.activeTexture(this.gl.TEXTURE1);
            this.gl.bindTexture(this.gl.TEXTURE_2D, this.headers[i].top);
            this.gl.activeTexture(this.gl.TEXTURE2);
            this.gl.bindTexture(this.gl.TEXTURE_2D, this.headers[i].front);
            this.gl.activeTexture(this.gl.TEXTURE3);
            this.gl.bindTexture(this.gl.TEXTURE_2D, this.headers[i].right);
            this.gl.activeTexture(this.gl.TEXTURE4);
            this.gl.bindTexture(this.gl.TEXTURE_2D, this.headers[i].left);

            // 上传贴图索引
            this.gl.uniform1i(this.headerDrawProgram.tFront, 2);
            this.gl.uniform1i(this.headerDrawProgram.tLeft, 4);
            this.gl.uniform1i(this.headerDrawProgram.tRight, 3);
            this.gl.uniform1i(this.headerDrawProgram.tTop, 1);

            // 绘制平铺
            for (let j = 0; j < this.headers[i].drawArray.length; j++) {

                // 旋转参数
                this.gl.uniform1f(this.headerDrawProgram.rotate,
                    this.headers[i].rotateArray[j]);

                // 坐标
                this.gl.uniform2f(this.headerDrawProgram.mp,
                    this.headers[i].drawArray[j], this.headers[i].drawY);

                // 开始绘制
                this.gl.drawArrays(this.gl.TRIANGLES, 0, 24);
            }
        }
    }
}
