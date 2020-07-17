package cc.yyf.book.service;

import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.Result;

import javax.servlet.http.Part;
import java.io.IOException;
import java.text.ParseException;

public interface BookService {
    /**
     * 用户发布书籍的service层
     * @param book
     * @param fileURI
     * @param part
     * @return
     */
    Result release(Book book, String fileURI, Part part);

    /**
     * 根据书籍id定位到特定书籍
     * @param bookId
     * @return
     */
    Result selectBookById(int bookId) throws IOException, ParseException;

    /**
     * 获取所有的书籍信息 通过es
     * @param from 起始位置
     * @param size 每页的个数
     * @return
     */
    Result getAllBook(int from, int size) throws IOException, ParseException;
}
