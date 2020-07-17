package cc.yyf.book.controller;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@CrossOrigin
@RestController
public class HomeController {

    @Autowired
    HomeService homeService;

    /**
     * 应用户主页可以获取用户的所有信息
     * @param request
     * @return
     */
    @GetMapping("/u/home")
    public Result home(HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return homeService.homeService(studentCode);
    }

    /**
     * 用户注销
     */
    @GetMapping("/u/logout")
    public Result logout(HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return homeService.logout(studentCode);
    }

    /**
     * 获取当前用户发布的书籍信息
     */
    @GetMapping("/u/search/book/own")
    public Result getBookMyself(@Valid @RequestBody Page page,
                                HttpServletRequest request) throws IOException, ParseException {
        String studentCode = (String) request.getAttribute("studentCode");
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();

        return homeService.getOwnBook(studentCode, from, size);
    }
}
