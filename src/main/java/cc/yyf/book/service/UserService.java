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

    /**
     * 通过用户的学号查询特定用户
     * @param studentCode
     * @return
     */
    Result selectUser(String studentCode);
}
