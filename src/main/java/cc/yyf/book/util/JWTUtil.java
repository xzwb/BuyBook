package cc.yyf.book.util;

import cc.yyf.book.exception.LoginTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具类
 */
public class JWTUtil {

    // token过期时间 30分钟 单位为秒
    private static final long EXPIRATION = 1800L;

    // 秘钥
    private static final String OWN = "Grow old along with me, the best is yet to be.";

    /**
     * 获取token
     * @param studentCode 学号
     * @return
     */
    public static String getToken(String studentCode) {
        // 设置过期时间为30分钟后
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION*1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                // 添加以下基本信息
                .withClaim("studentCode", studentCode)
                // 设置过期日期
                .withExpiresAt(expireDate)
                // 设置签发日期
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(OWN));
        return token;
    }


    // 验证token
    public static boolean verifierToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(OWN)).build();
        try {
            jwtVerifier.verify(token);
        } catch (TokenExpiredException e) {
            // 时间过期了
            return true;
        } catch (JWTVerificationException e) {
            // 验证失败
            throw new LoginTokenException();
        }
        return true;
    }
}
