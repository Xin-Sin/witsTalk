package top.xinsin.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.springframework.context.annotation.Configuration;
import top.xinsin.entity.BaseEntity;
import top.xinsin.lister.MyInsertListener;
import top.xinsin.lister.MyUpdateListener;

/**
 * @author xinsin
 * Created On 2023/9/21 15:28
 * @version 1.0
 */
@Configuration
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {
    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        MyInsertListener insertListener = new MyInsertListener();
        MyUpdateListener updateListener = new MyUpdateListener();
        globalConfig.registerInsertListener(insertListener, BaseEntity.class);
        globalConfig.registerUpdateListener(updateListener, BaseEntity.class);
    }
}
