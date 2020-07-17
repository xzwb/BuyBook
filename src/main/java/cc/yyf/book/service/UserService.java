package cc.yyf.book.service;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;

public interface UserService {

    /**
     * 查用户
     * @param page
     * @return
     */
    Result searchUser(Page page);
}
