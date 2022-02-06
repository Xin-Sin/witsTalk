package top.xinsin.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.xinsin.enums.MessageTypes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther wzp
 * @Date 2021/12/11 23:22
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public class Message {
    private int id;
    private String content;
    private String sender;
    private int recall;
    private String sendtime;
    private MessageTypes type;
    private String base64;
    public Message(String content, String sender,String type){
        this.content = content;
        this.sender = sender;
        this.type = MessageTypes.valueOf(type);
        this.sendtime = new SimpleDateFormat("y-M-d H:m:s").format(new Date());
    }
    public Message(String content, String sender,String type,String sendtime){
        this.content = content;
        this.sender = sender;
        this.type = MessageTypes.valueOf(type);
        this.sendtime = sendtime;
    }
    public Message(int id,String content,String sender,int recall,String type,String sendtime){
        this.content = content;
        this.sender = sender;
        this.id = id;
        this.recall = recall;
        this.type = MessageTypes.valueOf(type);
        this.sendtime = sendtime;
    }

}
