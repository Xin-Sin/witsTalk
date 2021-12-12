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

    private final Logger logger = LogManager.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @GetMapping("/api/message/get")
    public String getMessage(@RequestParam("min") int min,@RequestParam("max") int max){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        ArrayList<Message> messages = messageService.GetSomeMessage(min, max);
        JSONArray objects = new JSONArray();
        objects.addAll(messages);
        jsonObject.put("data",objects);
        logger.info("getMessage");
        return jsonObject.toJSONString();
    }

    @GetMapping("/api/message/getAll")
    public String getMessageAll(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        ArrayList<Message> messages = messageService.GetAllMessage();
        JSONArray objects = new JSONArray();
        objects.addAll(messages);
        jsonObject.put("data",objects);
        logger.info("getMessage");
        return jsonObject.toJSONString();
    }

    @PostMapping("/api/message/send")
    public String SendMessage(@RequestBody Message message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        messageService.SendMessage(message);
        logger.info("sendMessage");
        return jsonObject.toJSONString();
    }

    @PostMapping("/api/message/recall")
    public String Recall(@RequestBody Message message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        messageService.ReCall(message);
        logger.info("recallMessage");
        return jsonObject.toJSONString();
    }
}
