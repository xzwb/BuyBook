package cc.yyf.book.service;

import cc.yyf.book.pojo.BookUpdate;
import cc.yyf.book.pojo.Result;

import java.io.IOException;
import java.text.ParseException;

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

    /**
     * 获取用户自己发布的书籍信息
     * @param studentCode 当前用户的学号
     * @param from 偏移量
     * @param size 数据个数
     * @return
     */
    Result getOwnBook(String studentCode, int from, int size) throws IOException, ParseException;

    /**
     * 修改书籍的信息
     * @param studentCode
     * @param book
     * @return
     */
    Result updateBook(String studentCode, BookUpdate book);
}
