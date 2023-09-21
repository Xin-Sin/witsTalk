package top.xinsin.enums;

/**
 * @author xinsin
 * Created On 2023/4/3 15:47
 * @version 1.0
 */
public enum HttpCodes {
    /**
     * 200
     * 成功执行
     */
    HTTP_CODES200(200,"接口执行成功"),
    /**
     * 500
     * 服务器错误
     */

    HTTP_CODES500(500,"服务器出现错误,请联系服务器管理员"),
    /**
     * 401
     * 数据错误
     */
    HTTP_CODES401(401,"这是一个坏的请求,请重新写入参数"),
    /**
     * 555
     * 数据更新失败
     */
    HTTP_CODES555(555,"数据更新失败"),
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
    ACCESS_DENIED( 251,"无权访问");

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
