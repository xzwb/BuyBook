package cc.yyf.book.zfjw.service;


import cc.yyf.book.zfjw.exception.PublicKeyException;
import cc.yyf.book.zfjw.model.LoginStatus;
import org.apache.http.client.CookieStore;

import javax.security.auth.login.LoginException;

public interface LoginService {
    /**
     * 登录
     * @return 登录信息
     */
    LoginStatus login() throws PublicKeyException, LoginException, cc.yyf.book.zfjw.exception.LoginException;

    /**
     * 获取 cookie
     * @return cookie 值
     */
    CookieStore getCookie();
}
