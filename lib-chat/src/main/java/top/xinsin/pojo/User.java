package top.xinsin.pojo;


import lombok.Data;
import top.xinsin.Utils.Auth;

import java.util.Objects;

/**
 * @Auther wzp
 * @Date 2021/12/11 20:09
 * @Version 1.0
 */

//Entity User
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private Auth auth;
    private int online;
    private String last_login;
    public User(){
        this.username = null;
        this.password = null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
