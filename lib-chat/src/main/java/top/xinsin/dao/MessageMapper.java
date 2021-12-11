package top.xinsin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.Message;

import java.util.ArrayList;

/**
 * @Auther wzp
 * @Date 2021/12/11 23:24
 * @Version 1.0
 */
@Repository
public interface MessageMapper {
    ArrayList<Message> GetAllMessage();
    ArrayList<Message> GetSomeMessage(@Param("id_min") int id_min,@Param("id_max") int id_max);
    void SendMessage(Message message);
    void ReCall(Message message);
}
