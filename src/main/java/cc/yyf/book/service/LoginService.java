package cc.yyf.book.service;

import cc.yyf.book.pojo.Result;

public interface LoginService {
    /**
     * 登录
     * @param studentCode
     * @param password
     * @return
     */
    Result login(String studentCode, String password);
}
