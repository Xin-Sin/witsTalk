package top.xinsin.enums;

/**
 * @Auther wzp
 * @Date 2021/12/16 20:41
 * @Version 1.0
 */
public enum HttpStatus {
    OK(200,"success"), //成功
    INVALID(400,"InvalidRequest"), //无效请求
    NOTFOUND(404,"NotFound"), //未找到
    ERROR(500,"ServerError"), //服务器错误
    UNREALIZED(501,"Unrealized"), //未实现
    UNAUTHORIZED(401,"Unauthorized") //未授权
    ;
    public final int code;
    public final String status;

    private HttpStatus(int code, String status){
        this.code = code;
        this.status = status;
    }
}
