package cc.yyf.book.zfjw.manager;


import cc.yyf.book.zfjw.exception.PublicKeyException;
import cc.yyf.book.zfjw.util.RSA;
import org.apache.http.client.CookieStore;

import java.util.Map;

public interface KeyManager {

    /**
     * @see #getPublicKey(String, Map, CookieStore)
     * @return
     */
    RSA getPublicKey(String url) throws PublicKeyException, PublicKeyException;

    /**
     * @see #getPublicKey(String, Map, CookieStore)
     * @return
     */
    RSA getPublicKey(String url, CookieStore cookieStore) throws PublicKeyException;

    /**
     * @see #getPublicKey(String, Map, CookieStore)
     * @return
     */
    RSA getPublicKey(String url, Map<String, String> headers) throws PublicKeyException;

    /**
     * 获取公钥
     *
     * @param baseUrl 链接前缀
     * @param headers 自定义请求头
     * @param cookieStore 自定义 cookie
     * @return 公钥
     * @throws PublicKeyException 获取异常
     */
    RSA getPublicKey(String baseUrl, Map<String, String> headers, CookieStore cookieStore)
            throws PublicKeyException;

    /**
     * 使用公钥加密
     *
     * @param value 要加密的信息
     * @return 加密后的信息
     */
    String encryptPassword(RSA rsa, String value);
}
