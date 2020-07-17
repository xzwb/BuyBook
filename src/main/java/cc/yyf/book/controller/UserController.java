package cc.yyf.book.controller;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 查询用户
     * @param page
     * @return
     */
    @GetMapping("/u/search/user")
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
}
