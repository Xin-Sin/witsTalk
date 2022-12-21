import {useStore} from "../store";

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
export interface MessageData {
    id: number | undefined;
    content: string | undefined;
    sender: string | undefined;
    recall: boolean | undefined;
    sendtime: string | undefined;
    base64: string | undefined;
    type: MessageTypes | undefined;
}
export const isSelf = (message: MessageData): boolean => {
    const store = useStore();
    let name = null;
    if (store.userinfo !== null){
        name = store.userinfo.username
    }
    return message.sender === name;
}