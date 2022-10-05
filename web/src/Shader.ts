export default class Shader {

    /**
     * @type {WebGL2RenderingContext|WebGLRenderingContext}
     */
    gl: WebGL2RenderingContext | WebGLRenderingContext;

    /**
     * @type {WebGLProgram}
     */
    program;
    vPos: number = 0;
    vUv: number = 0;
    vFront: number = 0;
    vLeft: number = 0;
    vRight: number = 0;
    vTop: number = 0
    mvp: WebGLUniformLocation | null = null;
    tFront: WebGLUniformLocation | null = null;
    tLeft: WebGLUniformLocation | null = null;
    tRight: WebGLUniformLocation | null = null;
    tTop: WebGLUniformLocation | null = null;
    rotate: WebGLUniformLocation | null = null;
    mp: WebGLUniformLocation | null = null;


    /**
     * 创建编译 shader
     * @param {WebGL2RenderingContext|WebGLRenderingContext} gl
     * @param {String} vertex
     * @param {String} fragment
     * @return {WebGLProgram}
     */
    constructor(gl: WebGL2RenderingContext | WebGLRenderingContext, vertex: string, fragment: string) {

        this.gl = gl;

        vertex = vertex.replace("\n", "");
        fragment = fragment.replace("\n", "");

        // 创建程序
        this.program = gl.createProgram() as WebGLProgram;

        // 创建顶点着色器
        let vertexShader = gl.createShader(gl.VERTEX_SHADER) as WebGLShader;

        // 创建片段着色器
        let fragmentShader = gl.createShader(gl.FRAGMENT_SHADER) as WebGLShader;

        // 绑定源代码
        gl.shaderSource(vertexShader, vertex);
        gl.shaderSource(fragmentShader, fragment);

        // 编译
        gl.compileShader(vertexShader);
        gl.compileShader(fragmentShader);

        // 检测编译错误
        if (!gl.getShaderParameter(vertexShader, gl.COMPILE_STATUS)) {
            console.error("vertex:\r\n" + gl.getShaderInfoLog(vertexShader));
        }

        if (!gl.getShaderParameter(fragmentShader, gl.COMPILE_STATUS)) {
            console.error("fragment:\r\n" + gl.getShaderInfoLog(fragmentShader));
        }

        // 附加到程序
        gl.attachShader(this.program, vertexShader);
        gl.attachShader(this.program, fragmentShader);

        // 连接程序
        gl.linkProgram(this.program);

        // 检测链接错误
        if (!gl.getProgramParameter(this.program, gl.LINK_STATUS)) {
            console.error("link:\r\n" + gl.getProgramInfoLog(this.program));
        }
    }

    /**
     * 使用程序
     */
    use() {
        this.gl.useProgram(this.program);
    }

    /**
     * 获取 attribLocation
     * @param {String} attr
     */
    attribLocation(attr: string) {
        // @ts-ignore
        this[attr] = this.gl.getAttribLocation(this.program, attr);
    }

    /**
     * 获取 UniformLocation
     * @param {String} uni
     */
    uniformLocation(uni: string) {
        // @ts-ignore
        this[uni] = this.gl.getUniformLocation(this.program, uni);
    }
}