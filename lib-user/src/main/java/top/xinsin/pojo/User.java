package top.xinsin.pojo;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.xinsin.entity.BaseEntity;
import top.xinsin.enums.Auth;

import java.io.Serial;

/**
 * @author xinsin
 * Created On 2023/9/21 09:42
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class User extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -2983412068570683868L;

    private String username;
    private String password;
    private Auth auth = Auth.user;
    private int online;
    private String lastLogin;
    private String avatar;
    private String exclusiveColor;


}