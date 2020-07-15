package cc.yyf.book.controller;

import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.BookService;
import cc.yyf.book.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@CrossOrigin
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    /**
     * 用户发布书籍信息
     * @param book 书籍信息的类
     * @param request
     * @param part
     * @return
     */
    @PostMapping("/u/release")
    public Result release(@Valid Book book,
                          HttpServletRequest request,
                          @RequestPart("file") Part part) {
        book.setStudentCode((String) request.getAttribute("studentCode"));
        // 获取图书的发布时间
        book.setBookDate(new Date());
        // 雪花算法
        SnowFlake snowFlake = new SnowFlake(1, 2);
        book.setBookSrc(snowFlake.nextId()+".jpg");
        // 图片储存路径
        String fileURI = request.getSession().getServletContext().getRealPath("/") + book.getBookSrc();
        return bookService.release(book, fileURI, part);
    }

    /**
     * 根据书籍的id查询书籍信息
     * @param bookId
     * @return
     */
    @GetMapping("/u/search/book/{bookId}")
    public Result searchBookByBookId(@PathVariable("bookId") int bookId) throws IOException, ParseException {
        return bookService.selectBookById(bookId);
    }
}
