package top.xinsin.Task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.xinsin.pojo.User;
import top.xinsin.services.UserService;

import java.util.Date;

import static top.xinsin.controller.CheckController.checklist;

/**
 * @author wzp
 * @date 2021/12/12 11:03
 * @version 1.0
 */
@Configuration
@EnableScheduling
@Slf4j
public class CheckTask {
    private final UserService userService;
    @Autowired
    public CheckTask(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRate=1000)
    public void CheckTasks(){
        Date now = new Date();
        for (User user : checklist.keySet()) {
            Date time = checklist.get(user);
            if(time.getTime() + 1000*60*60*2 <= now.getTime()){
                userService.setOffline(user);
                checklist.remove(user);
                log.info("CheckList remove user=" + user);
            }
            log.info("CheckTask user=" + user);
        }
    }
}
