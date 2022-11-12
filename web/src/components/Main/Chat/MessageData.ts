export enum MessageTypes {
    /**
     * 文本消息
     */
    text,
    /**
     * 图片消息
     */
    img
}

export class MessageData {
    public id: number | undefined;
    public content: string | undefined;
    public sender: string | undefined;
    public recall: boolean | undefined;
    public sendtime: string | undefined;
    public base64: string | undefined;
    public type: MessageTypes | undefined;

    constructor(id: number, content: string, sender: string, recall: boolean, sendtime: string, base64: string, type: MessageTypes) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.recall = recall;
        this.sendtime = sendtime;
        this.base64 = base64;
        this.type = type;
    }

    public isSelf(): boolean {
        let name = window.sessionStorage.getItem("username");
        return this.sender === name;
    }
}