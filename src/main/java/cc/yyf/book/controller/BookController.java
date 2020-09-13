package cc.yyf.book.controller;

import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.BookService;
import cc.yyf.book.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@CrossOrigin(allowCredentials = "true")
@RestController
@Slf4j
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
        log.info("学号为: " + book.getStudentCode() + " 的用户发布书籍" + book);
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
    public Result searchBookByBookId(@PathVariable("bookId") int bookId,
                                     HttpServletRequest request) throws IOException, ParseException {
        String studentCode = (String) request.getAttribute("studentCode");
        return bookService.selectBookById(bookId, studentCode);
    }

    /**
     * 获取所有的书籍
     * @param page
     * @return
     */
    @PostMapping("/u/search/books")
    public Result searchAllBooks(@Valid @RequestBody Page page) throws IOException, ParseException {
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        return bookService.getAllBook(from, size);
    }


    /**
     * 对书籍进行模糊搜索
     * @param page
     * @return
     */
    @PostMapping("/u/search/some/book")
    public Result searchBook(@Valid @RequestBody Page page) throws IOException, ParseException {
        String message = page.getMessage();
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        return bookService.searchBook(message, from, size);
    }

    /**
     * 根据书籍种类模糊搜索
     * @param page
     * @return
     */
    @PostMapping("/u/style/book")
    public Result styleBook(@Valid @RequestBody Page page) throws IOException, ParseException {
        String message = page.getMessage();
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        return bookService.styleBook(message, from, size);
    }

    /**
     * 获取每一本书的用户访问量
     * @param bookId
     * @return
     */
    @GetMapping("/u/count/{bookId}")
    public Result countBook(@PathVariable("bookId") int bookId) {
        return bookService.countBook(bookId);
    }

}
