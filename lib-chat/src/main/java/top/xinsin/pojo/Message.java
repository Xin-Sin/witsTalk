package top.xinsin.pojo;

import lombok.Data;

/**
 * @Auther wzp
 * @Date 2021/12/11 23:22
 * @Version 1.0
 */
@Data
public class Message {
    int id;
    String content;
    String sender;
    int recall;
    public Message(String content, String sender){
        this.content = content;
        this.sender = sender;
    }
}
