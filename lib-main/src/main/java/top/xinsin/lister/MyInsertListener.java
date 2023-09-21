package top.xinsin.lister;

import com.mybatisflex.annotation.InsertListener;
import top.xinsin.entity.BaseEntity;
import top.xinsin.util.UserUtils;

import java.util.Date;

/**
 * @author xinsin
 * Created On 2023/9/21 15:30
 * @version 1.0
 */
public class MyInsertListener implements InsertListener {
    @Override
    public void onInsert(Object entity) {
        if (entity instanceof BaseEntity baseEntity){
            Long userId = UserUtils.getCurrentUserDTO().getId();
            baseEntity.setCreateId(userId);
            baseEntity.setCreateTime(new Date());
            baseEntity.setUpdateId(userId);
            baseEntity.setUpdateTime(new Date());
        }
    }
}
