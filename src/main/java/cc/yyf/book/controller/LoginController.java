package cc.yyf.book.controller;

import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin(allowCredentials = "true")
@RestController
@Slf4j
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
        log.info("***时间: " + new Date().toString() + " 登录: " + person);
        return loginService.login(person.getStudentCode(), person.getPassword());
    }
    
}
