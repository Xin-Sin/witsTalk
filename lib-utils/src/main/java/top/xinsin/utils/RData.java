package top.xinsin.utils;

import lombok.Data;
import top.xinsin.enums.HttpCodes;

/**
 * @author xinsin
 * @version 1.0
 * Created On 2022/5/14
 * @param <T> 返回值类型
 */
@Data
public class RData<T> {
    private int status;
    private String message;
    private T data;
    private long timestamp;

    public RData() {
        this.timestamp = System.currentTimeMillis();
    }
    public static<T> RData<T> success(T data){
        RData<T> rData = new RData<>();
        rData.setStatus(HttpCodes.HTTP_CODES200.getCode());
        rData.setMessage(HttpCodes.HTTP_CODES200.getMessage());
        rData.setData(data);
        return rData;
    }
    public static<T> RData<T> failed(HttpCodes httpCodes, T data){
        RData<T> rData = new RData<>();
        rData.setStatus(httpCodes.getCode());
        rData.setMessage(httpCodes.getMessage());
        rData.setData(data);
        return rData;
    }
}
