package cc.yyf.book.service;

import cc.yyf.book.pojo.Result;

public interface HomeService {
    /**
     * 根据用户学号获取用户信息
     * @param studentCode
     * @return
     */
    Result homeService(String studentCode);

    /**
     * 用户注销
     * @param studentCode
     * @return
     */
    Result logout(String studentCode);

}
