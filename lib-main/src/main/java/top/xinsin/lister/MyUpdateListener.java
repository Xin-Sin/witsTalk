package top.xinsin.lister;

import com.mybatisflex.annotation.UpdateListener;
import top.xinsin.entity.BaseEntity;
import top.xinsin.util.UserUtils;

import java.util.Date;

/**
 * @author xinsin
 * Created On 2023/9/21 15:30
 * @version 1.0
 */
public class MyUpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object entity) {
        if (entity instanceof BaseEntity baseEntity){
            baseEntity.setUpdateId(UserUtils.getCurrentUserDTO().getId());
            baseEntity.setUpdateTime(new Date());
        }
    }
}
