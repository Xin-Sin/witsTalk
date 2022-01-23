package top.xinsin.pojo;


import lombok.Data;
import lombok.experimental.Accessors;
import top.xinsin.enums.Auth;

/**
 * @Auther wzp
 * @Date 2021/12/11 20:09
 * @Version 1.0
 */

//Entity User
@Data
@Accessors(chain = true)
public class User {

    private int id;
    private String username;
    private String password;
    private Auth auth;
    private int online;
    private String last_login;
    private String base64;

    public User(){
        this.username = null;
        this.password = null;
        this.auth = Auth.user;
    }
    public User(String username){
        this.username = username;
        this.auth = Auth.user;
    }
    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.auth = Auth.user;
    }
    public User(String username,String password,String auth){
        this.username = username;
        this.password = password;
        this.auth = Auth.valueOf(auth);
    }
}
