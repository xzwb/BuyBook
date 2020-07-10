package cc.yyf.book.controller;

import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 登录
     * @param person 用户对象
     * @return
     */
    @PostMapping("/a/login")
    public Result login(Person person) {
        return loginService.login(person.getStudentCode(), person.getPassword());
    }
}
