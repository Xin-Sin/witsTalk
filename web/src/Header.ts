/**
 * @class Header
 * 玩家头颅
 *
 * Copyright 2021-08-02 MrKBear mrkbear@qq.com
 * All rights reserved.
 * This file is part of the App project.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * */
export default class Header {

    // 四个朝向的贴图
    frontImg = new Image();
    leftImg = new Image();
    rightImg = new Image();
    topImg = new Image();

    // 四个朝向的贴图源地址
    frontSrc = "";
    leftSrc = "";
    rightSrc = "";
    topSrc = "";

    // 四个方向贴图
    front;
    left;
    right;
    top;

    /**
     * @type {WebGL2RenderingContext|WebGLRenderingContext}
     */
    gl;
    /**
     * 绘制 X 数组
     * @type {Number[]}
     */
    drawArray: number[] = [];
    /**
     * 绘制高度
     * @type {Number}
     */
    drawY = 0;
    /**
     * 旋转列表
     */
    rotateArray: number[] = [];
    /**
     * 速度
     */
    speedIn = 20;
    speedOut = 5;
    emitIndex = -1;

    /**
     * @param {WebGL2RenderingContext|WebGLRenderingContext} gl
     */
    constructor(gl: WebGL2RenderingContext | WebGLRenderingContext) {
        this.gl = gl;
        this.front = this.gl.createTexture();
        this.left = this.gl.createTexture();
        this.right = this.gl.createTexture();
        this.top = this.gl.createTexture();
    }

    /**
     * 加载全部图片
     * @return Promise
     */
    loadImg() {

        // 设置源地址
        this.frontImg.src = this.frontSrc;
        this.leftImg.src = this.leftSrc;
        this.rightImg.src = this.rightSrc;
        this.topImg.src = this.topSrc;

        // 上传到贴图数据
        return Promise.all([
            new Promise(r => this.frontImg.onload = () => r(this.frontImg)),
            new Promise(r => this.leftImg.onload = () => r(this.leftImg)),
            new Promise(r => this.rightImg.onload = () => r(this.rightImg)),
            new Promise(r => this.topImg.onload = () => r(this.topImg))
        ]);
    }

    getTexture(v: string): WebGLTexture | null {
        if (v === 'front') {
            return this.front;
        }
        if (v === 'left') {
            return this.left;
        }
        if (v === 'right') {
            return this.right;
        }
        if (v === 'top') {
            return this.top;
        }
        return null;
    }

    getTexImgSource(v: string): TexImageSource | ArrayBufferView | null {
        if (v === 'front') {
            return this.frontImg;
        }
        if (v === 'left') {
            return this.leftImg;
        }
        if (v === 'right') {
            return this.rightImg;
        }
        if (v === 'top') {
            return this.topImg;
        }
        return null;
    }

    /**
     * 上传贴图
     */
    updateImg(ver: number) {
        ["front", "left", "right", "top"].map((v) => {
            this.gl.bindTexture(this.gl.TEXTURE_2D, this.getTexture(v));
            this.gl.texParameteri(this.gl.TEXTURE_2D, this.gl.TEXTURE_MAG_FILTER, this.gl.NEAREST);
            this.gl.texParameteri(this.gl.TEXTURE_2D, this.gl.TEXTURE_MIN_FILTER, this.gl.NEAREST);

            // 如果是gl1
            if (ver === 1) {
                this.gl.texImage2D(this.gl.TEXTURE_2D, 0, this.gl.RGBA,
                    this.gl.RGBA, this.gl.UNSIGNED_BYTE, this.getTexImgSource(v) as TexImageSource);
            } else {
                this.gl.texImage2D(this.gl.TEXTURE_2D, 0, this.gl.RGBA, 8, 8, 0,
                    this.gl.RGBA, this.gl.UNSIGNED_BYTE, this.getTexImgSource(v) as ArrayBufferView);
            }
        });
    }

    /**
     * 设置源地址
     * @param {String} f 正面链接
     * @param {String} l 左面链接
     * @param {String} r 右面链接
     * @param {String} b 背面链接
     * @return Header
     */
    setSrc(f: string, l: string, r: string, b: string) {
        this.frontSrc = f;
        this.leftSrc = l;
        this.rightSrc = r;
        this.topSrc = b;
        return this;
    }

    /**
     * 通过标准链接设置src
     * @param {String} url
     * @param {String} [type="png"]
     * @return this
     */
    setSrcByUrl(url: string, type = "png") {
        return this.setSrc(
            url + "front." + type,
            url + "left." + type,
            url + "right." + type,
            url + "top." + type
        )
    }

    /**
     * 设置绘制数组
     * @param {Number} x 相位
     * @param {Number} y 绘制高度
     * @param {Number} ux 单位距离
     */
    setDrawArray(x: number, y: number, ux: number) {

        // 设置绘制高度
        this.drawY = y;

        // 清除数组
        this.drawArray = [];

        // 标记中心点
        this.drawArray.push(x);
        if (this.rotateArray[0] === undefined) this.rotateArray.push(0);

        // 下一个位置
        let nextPosL = 0;
        let nextPosR = 0;
        let time = 0;

        // 循环生成数据
        while (true) {

            // 边界约束
            if (nextPosL < -1 && nextPosR > 1) break;
            time++;
            // 安全执行次数
            if (time > 9999) break;

            // 生成左侧数据
            nextPosL = x - time * ux;

            // 生成右侧数据
            nextPosR = x + time * ux;

            // 添加
            this.drawArray.push(nextPosL);
            if (this.rotateArray[this.drawArray.length - 1] === undefined)
                this.rotateArray.push(0);

            this.drawArray.push(nextPosR);
            if (this.rotateArray[this.drawArray.length - 1] === undefined)
                this.rotateArray.push(0);
        }
    }

    /**
     * 更新
     */
    update(t: number) {
        for (let i = 0; i < this.rotateArray.length; i++) {

            // 旋转
            if (i === this.emitIndex) {
                this.rotateArray[i] += (1 - this.rotateArray[i]) * t * this.speedIn;
            }

            // 回转
            else {
                this.rotateArray[i] -= (this.rotateArray[i]) * t * this.speedOut;
            }

            // 限制
            if (this.rotateArray[i] > 1) this.rotateArray[i] = 1;
            if (this.rotateArray[i] < 0) this.rotateArray[i] = 0;

        }

    }

    /**
     * 旋转
     * @param {Number} x 事件坐标X
     * @param {Number} a 事件半径
     * @param {Number} zoom 事件半径
     * @param {Number} disY Y轴偏移距离
     * @param {Number} ratio 分辨率
     */
    rotate(x: number, a: number, zoom: number, disY: number, ratio: number) {

        // X 测试
        for (let i = 0; i < this.drawArray.length; i++) {

            // 半径约束
            let disX = Math.abs(this.drawArray[i] - x);

            // 射线检测
            if ((disX < zoom) && ((a - disY) > (disX * ratio / 3 ** .5))) {
                this.emitIndex = i;
            }
        }
    }
}
