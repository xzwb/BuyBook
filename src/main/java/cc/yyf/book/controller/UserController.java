package cc.yyf.book.controller;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@RestController
@CrossOrigin(allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 查询用户
     * @param page
     * @return
     */
    @PostMapping("/u/search/user")
    public Result searchUser(@Valid @RequestBody Page page) {
        return userService.searchUser(page);
    }

    /**
     * 查询特定用户信息
     * @param studentCode
     * @return
     */
    @GetMapping("/u/select/user/{studentCode}")
    public Result selectUser(@PathVariable("studentCode") String studentCode) {
        return userService.selectUser(studentCode);
    }

    /**
     * 根据学号获取该学号用户发布的书籍信息
     * @return
     */
    @PostMapping("/u/book/user")
    public Result getBookByUser(@Valid @RequestBody Page page) throws IOException, ParseException {
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        String studentCode = page.getMessage();
        return userService.usersBook(studentCode, from, size);
    }
}
