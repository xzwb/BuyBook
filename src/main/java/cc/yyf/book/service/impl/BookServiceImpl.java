package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.BookMapper;
import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.BookService;
import cc.yyf.book.thread.SaveFileThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    /**
     * 用户发布书籍的service层
     * @param book
     * @param fileURI
     * @param part
     * @return
     */
    @Override
    public Result release(Book book, String fileURI, Part part) {
        bookMapper.insertBook(book);
        new SaveFileThread(part, fileURI).run();
        return Result.build(ResultStatusEnum.SUCCESS, book);
    }
}
