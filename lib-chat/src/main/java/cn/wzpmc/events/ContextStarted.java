package cn.wzpmc.events;

import cn.wzpmc.dao.ChatDao;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ContextStarted implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        ChatDao bean = applicationContext.getBean(ChatDao.class);
        bean.initTable();
    }
}
