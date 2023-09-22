package top.xinsin.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xinsin.enums.HttpCodes;
import top.xinsin.util.Result;

/**
 * @author xinsin
 * Created On 2023/9/22 10:59
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(HttpServletRequest req, Exception e){
        log.error("发生异常原因是:",e);
        return Result.failed(HttpCodes.HTTP_CODES500, e.getMessage());
    }

}
