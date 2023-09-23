package top.xinsin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xinsin.mapper.LogMapper;
import top.xinsin.pojo.Log;
import top.xinsin.service.LogService;

/**
 * @author xinsin
 * Created On 2023/9/23 11:46
 * @version 1.0
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
}
