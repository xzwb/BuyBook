package cc.yyf.book.interceptor;

import cc.yyf.book.exception.LoginTokenException;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.util.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTInterceptor extends HandlerInterceptorAdapter {
    /**
     * redis 模板类
     */
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 使用JWT鉴定权限
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            throw new LoginTokenException();
        }
        Date expire = JWT.decode(token).getExpiresAt();
        // 学号
        String studentCode = JWT.decode(token).getClaim("studentCode").asString();
        // 多设备登录只能登录在一个设备
        if (!token.equals(redisTemplate.opsForValue().get(studentCode))) {
            throw new LoginTokenException();
        }
        request.setAttribute("studentCode", studentCode);
        // token校验
        if (JWTUtil.verifierToken(token)) {
            if (expire.before(new Date())) {
                if (redisTemplate.opsForValue().get(studentCode).equals(token)) {
                    token = JWTUtil.getToken(studentCode);
                    redisTemplate.opsForValue().set(studentCode, token, 1800, TimeUnit.SECONDS);
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    Result result = Result.build(ResultStatusEnum.TOKEN_CHANGE, token);
                    out.write(JSON.toJSONString(result));
                    out.flush();
                    out.close();
                    return false;
                }
            }
            redisTemplate.opsForValue().set(studentCode, token, 1800, TimeUnit.SECONDS);
        }

        return true;
    }
}
