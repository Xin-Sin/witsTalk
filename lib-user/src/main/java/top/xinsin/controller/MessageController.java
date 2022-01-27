package top.xinsin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.pojo.Message;
import top.xinsin.services.MessageService;

import java.util.ArrayList;

/**
 * @Auther wzp
 * @Date 2021/12/11 23:24
 * @Version 1.0
 */
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/user/api/message/count")
    public String getAllMessageCount(){
        return messageService.GetAllMessageCount().toString();
    }

    @GetMapping("/user/api/message/get")
    public String getMessage(@RequestParam("min") int min,@RequestParam("max") int max){
        return messageService.GetSomeMessage(min,max).toString();
    }

    @GetMapping("/user/api/message/getAll")
    public String getMessageAll(){
        return messageService.GetAllMessage().toString();
    }

    @PostMapping("/user/api/message/send")
    public String SendMessage(@RequestBody String strContent){
        return messageService.SendMessage(strContent).toString();
    }

    @PostMapping("/user/api/message/recall")
    public String Recall(@RequestBody Message message){
        return messageService.ReCall(message).toString();
    }

    @GetMapping("/user/api/message/getOnlineUser")
    public String getPlayerCount(){
        return messageService.getPlayerCount().toString();
    }
}
