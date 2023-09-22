package top.xinsin.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.xinsin.pojo.User;
import top.xinsin.service.impl.UserServiceImpl;
import top.xinsin.util.JwtTokenUtil;
import top.xinsin.util.Result;
import top.xinsin.util.SecurityUtils;
import top.xinsin.util.UserUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author xinsin
 * Created On 2023/9/20 14:12
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserServiceImpl userService;
    private final StringRedisTemplate redisTemplate;

    //redis路由键
    @Value("${jwt.redis_token_key}")
    private String redisTokenKey;

    //redis过期时间（同JWT过期时间）
    @Value("${jwt.expiration}")
    private int expirationTime;

    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserServiceImpl userService, StringRedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(path = "/debug",method = RequestMethod.GET)
    public Result<Integer> debug(){
        return Result.success(1/0);
    }

    @RequestMapping(path = "/login", method =  RequestMethod.POST)
    public Result<String> login(@RequestBody User user){
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        SecurityUtils.login(user.getUsername(), user.getPassword(),authenticationManager);

        User user1 = UserUtils.getUserByUsername(user.getUsername());
        String token = jwtTokenUtil.generateToken(user1.getUsername());

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set( String.format(redisTokenKey,user1.getUsername()), token, expirationTime, TimeUnit.MINUTES);

        return Result.success(token);
    }
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void register(@RequestBody User user){
        userService.register(user);
    }
}
