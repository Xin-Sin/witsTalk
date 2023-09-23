package top.xinsin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.xinsin.entity.BaseEntity;

import java.io.Serial;

/**
 * @author xinsin
 * Created On 2023/9/23 11:39
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Log extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8331135701252173690L;

    private String requestClass;
    private String classMethod;
    private String requestUrl;
    private String requestMethod;
    private Integer status;
    private String data;
    private Long timestamp;
    private String message;

    public Log(String requestClass, String classMethod, String requestUrl, String requestMethod, Integer status, String data, Long timestamp, String message) {
        this.requestClass = requestClass;
        this.classMethod = classMethod;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
        this.message = message;
    }
}
