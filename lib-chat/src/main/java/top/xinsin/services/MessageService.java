package top.xinsin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.pojo.Message;
import top.xinsin.dao.MessageMapper;

import java.util.ArrayList;

/**
 * @Auther wzp
 * @Date 2021/12/11 23:24
 * @Version 1.0
 */
@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    public ArrayList<Message> GetAllMessage(){
        return messageMapper.GetAllMessage();
    }

    public ArrayList<Message> GetSomeMessage(int id_min,int id_max){
        return messageMapper.GetSomeMessage(id_min,id_max);
    }

    public void SendMessage(Message message){
        messageMapper.SendMessage(message);
    }

    public void ReCall(Message message){
        messageMapper.ReCall(message);
    }
}
