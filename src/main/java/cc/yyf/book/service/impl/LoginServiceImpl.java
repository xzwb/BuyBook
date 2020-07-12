package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.LoginMapper;
import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.LoginService;
import cc.yyf.book.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    LoginMapper loginMapper;

    /**
     * 登录验证
     * @param studentCode
     * @param password
     * @return
     */
    @Override
    public Result login(String studentCode, String password) {
        // 密码是加密后存入数据库的
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Person person = loginMapper.loginSelect(studentCode, password);
        if (person != null) {
            // 获取token
            String token = JWTUtil.getToken(studentCode);
            redisTemplate.opsForValue().set(studentCode, token, 1800, TimeUnit.SECONDS);
            return Result.build(ResultStatusEnum.SUCCESS, token);
        }
        return Result.build(ResultStatusEnum.LOGIN_FALSE);
    }
}
