package cc.yyf.book.service;

import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.Result;

import javax.servlet.http.Part;

public interface BookService {
    /**
     * 用户发布书籍的service层
     * @param book
     * @param fileURI
     * @param part
     * @return
     */
    Result release(Book book, String fileURI, Part part);
}
