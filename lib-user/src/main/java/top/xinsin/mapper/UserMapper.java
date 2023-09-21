package top.xinsin.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.xinsin.pojo.User;

/**
 * @author xinsin
 * Created On 2023/9/21 09:59
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByUserName(String userName);
    Boolean isUserExist(String userName);
}
