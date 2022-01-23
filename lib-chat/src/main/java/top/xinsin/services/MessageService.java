package top.xinsin.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.Utils.ResponseData;
import top.xinsin.pojo.Message;
import top.xinsin.dao.MessageMapper;

import java.util.ArrayList;
import java.util.Comparator;

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

    public ResponseData GetAllMessage(){
        log.info("GetAllMessage");
        return new ResponseData(messageMapper.GetAllMessage());
    }

    public ResponseData GetSomeMessage(Integer id_min,Integer id_max){
        log.info("GetSomeMessage args:id_min=" + id_min + ";id_max=" + id_max);
        ArrayList<Message> messages = messageMapper.GetSomeMessage(id_min, id_max);
        messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return new ResponseData(messages);
    }

    public ResponseData SendMessage(Message message){
        log.info("SendMessage args:message=" + message);
        messageMapper.SendMessage(message);
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
        return new ResponseData(messageMapper.GetAllMessageCount());
    }
}
