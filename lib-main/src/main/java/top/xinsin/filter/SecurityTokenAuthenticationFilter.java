package top.xinsin.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.xinsin.pojo.User;
import top.xinsin.pojo.Vo.UserVo;
import top.xinsin.util.JwtTokenUtil;
import top.xinsin.util.UserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xinsin
 * Created On 2023/9/20 17:47
 * @version 1.0
 */
@Slf4j
@Component
public class SecurityTokenAuthenticationFilter extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;

    private final JwtTokenUtil jwtTokenUtil;

    //请求头
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    //请求令牌中特定的字符序列
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    //redis路由键
    @Value("${jwt.redis_token_key}")
    private String redisTokenKey;

    public SecurityTokenAuthenticationFilter(StringRedisTemplate redisTemplate, JwtTokenUtil jwtTokenUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authToken = parseJwt(request);
            assert authToken != null;
            String username = jwtTokenUtil.getTokenAsUsername(authToken);
            //判断redis中是否存在相应的token信息
            if (Boolean.TRUE.equals(redisTemplate.hasKey(String.format(redisTokenKey, username)))) {
                //从redis中取出相应的用户信息详情
                ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
                String token = valueOperations.get(String.format(redisTokenKey, username));
                if (authToken.equals(token)){
                    List<GrantedAuthority> authorityList = new ArrayList<>();
                    User userByUsername = UserUtils.getUserByUsername(username);
                    //简单呈现用户名和密码
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(userByUsername.getUsername(), userByUsername.getPassword(), authorityList), token, authorityList);
                    //从HttpServletRequest对象构建详细信息对象，并设置详情
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //将给定的SecurityContext与当前执行线程相关联
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            log.error("无法设置用户认证:", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 从 Authorization 标头中，提取令牌
     *
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(tokenHeader);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(tokenHead)) {
            return headerAuth.substring(tokenHead.length());
        }
        return null;
    }
}
