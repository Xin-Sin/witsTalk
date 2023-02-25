package top.xinsin.events;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.xinsin.dao.FileMapper;

@Component
public class ContextStarted implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        FileMapper bean = applicationContext.getBean(FileMapper.class);
        bean.initTable();
    }
}
