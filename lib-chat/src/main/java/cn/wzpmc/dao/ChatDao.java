package cn.wzpmc.dao;

import cn.wzpmc.pojo.Message;

/**
 * @Author wzp
 * @Date 2022/1/26
 * @Version 1.0
 */
public interface ChatDao {
    /**
     * send a message to database
     * @param message the message you want to send
     */
    void sendMessage(Message message);
}
