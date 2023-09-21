package top.xinsin.pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.xinsin.enums.Auth;

import java.io.Serial;
import java.util.Collection;

/**
 * @author xinsin
 * Created On 2023/9/21 09:55
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements UserDetails {
    @Serial
    private static final long serialVersionUID = -8504207797446612053L;

    private int id;
    private String username;
    private String password;
    private Auth auth = Auth.user;
    private int online;
    private String lastLogin;
    private String exclusiveColor;
    private String base64;
    private String token;

    public UserVo(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
