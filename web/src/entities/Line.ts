export interface Line {
    y: number;
    rotation: number;
    draw(render: CanvasRenderingContext2D): void;
}
export class Line implements Line{
    y: number;
    rotation: number;
    constructor(y: number,rotation: number) {
        this.y = y;
        this.rotation = rotation
    }
    draw(render: CanvasRenderingContext2D) {
        const image = new Image();
        image.src = "./src/assets/line.png";
        image.alt = "NoThing";
        image.onload = () => {
            render.drawImage(image, 0, this.y ,render.canvas.width,image.height);
            render.rotate(this.rotation * Math.PI / 180);
        }
        //render.drawImage()
    }
}