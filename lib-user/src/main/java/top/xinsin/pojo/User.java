package top.xinsin.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.xinsin.enums.Auth;

/**
 * @author wzp
 * Created On 2021/12/11 20:09
 * @version 1.0
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private Auth auth = Auth.user;
    private int online;
    private String lastLogin;
    private String exclusiveColor;
    private String base64;
    public User(String username){
        this.username = username;
    }
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }
    public User(String username,String password,String auth){
        this.username = username;
        this.password = password;
        this.auth = Auth.valueOf(auth);
    }
}
