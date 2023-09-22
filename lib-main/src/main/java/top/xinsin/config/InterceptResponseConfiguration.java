package top.xinsin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.xinsin.util.Result;

import java.util.Objects;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InterceptResponseConfiguration implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        只有返回数据为自定义返回格式,才对数据进行处理
        if (body instanceof Result<?> r){
            String controllerMethodName = Objects.requireNonNull(returnType.getMethod()).getName();
            String controllerClassName = returnType.getDeclaringClass().getSimpleName();
            String controllerClassURlPath = returnType.getDeclaringClass().getAnnotation(RequestMapping.class).value()[0];
            RequestMapping annotation = returnType.getMethod().getAnnotation(RequestMapping.class);
            String controllerUrlPath = annotation.path()[0];
            String controllerUrlMethod = annotation.method()[0].name();
            log.info("[全局响应体后处理器]-请求类:{}-请求方法:{}-请求地址:{}{}-请求方法:{}-响应状态码:{}-响应数据:{}",
                    controllerClassName,
                    controllerMethodName,
                    controllerClassURlPath,
                    controllerUrlPath,
                    controllerUrlMethod,
                    r.getStatus(),
                    r.getData());
            response.setStatusCode(HttpStatusCode.valueOf(r.getStatus()));
        }
        return body;
    }
}
