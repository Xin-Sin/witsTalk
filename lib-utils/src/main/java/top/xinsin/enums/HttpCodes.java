package top.xinsin.enums;

/**
 * @author xinsin
 * Created On 2022/5/14
 * @version 1.0
 */

public enum HttpCodes {
    /**
     * 200
     * 成功执行
     */
    HTTP_CODES200(200,"接口一不小心执行成功啦!"),
    /**
     * 500
     * 服务器错误
     */

    HTTP_CODES500(500,"哎呀,错误了请节哀!"),
    /**
     * 401
     * 数据错误
     */
    HTTP_CODES401(401,"你这请求是坏的,你不会写吗?"),
    /**
     * 501
     * 账号密码错误
     */
    HTTP_CODES501(501,"帐号或密码错误!"),
    /**
     * 250
     * token验证错误
     */
    INVALID_TOKEN(250,"令牌验证错误"),
    /**
     * 251
     * 无权限访问
     */
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
