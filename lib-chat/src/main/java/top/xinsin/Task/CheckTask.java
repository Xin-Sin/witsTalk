package top.xinsin.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

import java.util.Date;

import static top.xinsin.controller.CheckController.checklist;

/**
 * @Auther wzp
 * @Date 2021/12/12 11:03
 * @Version 1.0
 */
@Configuration
@EnableScheduling
public class CheckTask {
    @Autowired
    UserService userService;
    @Scheduled(fixedRate=1000)
    public void CheckTasks(){
        Date now = new Date();
        for (User user : checklist.keySet()) {
            Date time = checklist.get(user);
            if(time.getTime() + 5000 <= now.getTime()){
                userService.setOffline(user);
                checklist.remove(user);
            }
        }
    }
}
