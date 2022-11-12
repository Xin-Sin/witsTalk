package cn.wzpmc.dao;

import cn.wzpmc.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author wzp
 */
@Mapper
@Repository
public interface ChatDao {
    /**
     * send a message to database
     * @param message the message you want to send
     */
    void sendMessage(Message message);

    /**
     * get message with id > id_min and id < id_max
     *
     * @param count the count of message
     * @param idMin the max value of id
     * @return some message objects
     */
    ArrayList<Message> getMessage(@Param("id_min") int idMin, @Param("count") int count);

    /**
     * get all messages count
     * @return count of message
     */
    ArrayList<Integer> getCount();

    /**
     * get a user's head portrait with username
     * @param username username
     * @return a base64 of user's head portrait
     */
    String getUserHeadPortrait(@Param("username") String username);

    /**
     * recall a message
     * @param message a message object
     */
    void recall(Message message);
}
