package top.xinsin.util;

import lombok.Data;
import top.xinsin.enums.HttpCodes;

/**
 * @author xinsin
 * Created On 2023/9/21 15:05
 * @version 1.0
 */
@Data
public class Result<T> {
    private int status;
    private String message;
    private T data;
    private long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }
    public static<T> Result<T> success(T data){
        Result<T> rData = new Result<>();
        rData.setStatus(HttpCodes.HTTP_CODES200.getCode());
        rData.setMessage(HttpCodes.HTTP_CODES200.getMessage());
        rData.setData(data);
        return rData;
    }
    public static<T> Result<T> failed(HttpCodes httpCodes, T data){
        Result<T> rData = new Result<>();
        rData.setStatus(httpCodes.getCode());
        rData.setMessage(httpCodes.getMessage());
        rData.setData(data);
        return rData;
    }
}
