let lastTime = 0;

export default class Tick {

    // 总用时
    allTime = 0;

    // 速率
    speed = 1;

    /**
     * 动画循环
     * @param {Function} fn
     */
    constructor(fn: Function) {

        // 主循环
        let loop = (t: number) => {

            // 时差
            let dur = (t - this.allTime) * this.speed / 1000;

            // 检测由于失焦导致的丢帧
            if (t - this.allTime < 100) {
                fn(dur);
            }

            // 更新时间
            this.allTime = t;

            // 继续循环
            requestAnimationFrame(loop);
            // this.requestNextFrame(loop);

        }

        // 获取时间
        requestAnimationFrame((t) => {

            // 记录初始时间
            this.allTime = t;

            // 开启循环
            requestAnimationFrame(loop);
        })
    }

    requestNextFrame(fn: Function) {
        setTimeout(() => {
            let currTime = new Date().getTime();
            let timeToCall = Math.max(0, 16 - (currTime - lastTime));
            fn(currTime + timeToCall);
        }, 1);
    };
}