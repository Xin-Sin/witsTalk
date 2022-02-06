package top.xinsin.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.Utils.ResponseData;
import top.xinsin.dao.MessageMapper;
import top.xinsin.dao.UserMapper;
import top.xinsin.pojo.Message;

import java.util.ArrayList;

/**
 * @Auther wzp
 * @Date 2021/12/11 23:24
 * @Version 1.0
 */
@Service
@Slf4j
public class MessageService {

    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserMapper userMapper;

    public ResponseData GetAllMessage(){
        log.info("GetAllMessage");
        return new ResponseData(messageMapper.GetAllMessage());
    }

    public ResponseData GetSomeMessage(Integer id_min,Integer id_max){
        log.info("GetSomeMessage args:id_min=" + id_min + ";id_max=" + id_max);
        ArrayList<Message> messages = messageMapper.GetSomeMessage(id_min, id_max);
        return new ResponseData(messages);
    }

    public ResponseData SendMessage(String message){
        JSONObject jsonObject = JSON.parseObject(message);
        String sender = (String) jsonObject.get("sender");
        String content = (String) jsonObject.get("content");
        String type = (String) jsonObject.get("type");
        Message m = new Message(content,sender,type);
        log.info("SendMessage args:message=" + m);
        messageMapper.SendMessage(m);
        return new ResponseData();
    }

    public ResponseData ReCall(Message message){
        log.info("ReCall args:message=" + message);
        messageMapper.ReCall(message);
        return new ResponseData();
    }

    public ResponseData getPlayerCount(){
        log.info("getPlayerCount");
        return new ResponseData(messageMapper.getPlayerCount());
    }

    public ResponseData GetAllMessageCount(){
        log.info("GetAllMessageCount");
        return new ResponseData(messageMapper.GetAllMessage().size());
    }
}
