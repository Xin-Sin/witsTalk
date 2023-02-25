package top.xinsin.events;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.xinsin.dao.RouteMapper;
import top.xinsin.dao.UserMapper;

@Component
public class ContextStarted implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        UserMapper bean = applicationContext.getBean(UserMapper.class);
        RouteMapper bean1 = applicationContext.getBean(RouteMapper.class);
        bean.initTable();
        bean1.initTable();
    }
}
