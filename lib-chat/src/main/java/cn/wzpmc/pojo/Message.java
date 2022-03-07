package cn.wzpmc.pojo;

import lombok.*;
import top.xinsin.enums.MessageTypes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author wzp
 * @Date 2022/1/26
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int id;
    private String content;
    private String sender;
    private int recall;
    private String sendtime;
    private String base64;
    MessageTypes type;
    public Message(int id){
        this.id = id;
    }
    public Message(int id, String content, String sender, int recall, String sendtime, String type, String base64){
        this.content = content;
        this.sender = sender;
        this.id = id;
        this.recall = recall;
        this.type = MessageTypes.valueOf(type);
        this.sendtime = sendtime;
        this.base64 = base64;
    }
    public Message(String content, String sender,String type){
        this.content = content;
        this.sender = sender;
        this.type = MessageTypes.valueOf(type);
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        this.sendtime = dateFormat.format(calendar.getTime());
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

