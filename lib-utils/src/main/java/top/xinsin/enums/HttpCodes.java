package top.xinsin.enums;

public enum HttpCodes {
    HTTP_CODES200(200,"接口一不小心执行成功啦!"),

    HTTP_CODES500(500,"哎呀,错误了请节哀!"),
    HTTP_CODES401(401,"你这请求是坏的,你不会写吗?"),
    HTTP_CODES501(501,"帐号或密码错误!"),
    INVALID_TOKEN(250,"令牌验证错误"),
    ACCESS_DENIED( 251,"你没有权限访问");

    private final int code;
    private final String message;

    HttpCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
