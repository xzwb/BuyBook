package cc.yyf.book.service;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;

import java.io.IOException;
import java.text.ParseException;

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

    /**
     * 获取该学号用户发布的书籍信息
     * @param studentCode
     * @param from
     * @param size
     * @return
     */
    Result usersBook(String studentCode, int from, int size) throws ParseException, IOException;
}
