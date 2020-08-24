package cc.yyf.book.controller;

import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.RegisterService;
import cc.yyf.book.util.SnowFlake;
import cc.yyf.book.zfjw.exception.PublicKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.util.Map;

/**
 * 注册
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@Slf4j
public class RegisterController {

    @Autowired
    RegisterService registerService;

    /**
     * 获取短信验证码
     * @param phoneNumber
     * @return
     */
    @PostMapping("/a/smsCode")
    public Result getSMSCode(@RequestBody Map<String, String> phoneNumber) {
        return registerService.getSMSCode(phoneNumber.get("phoneNumber"));
    }

    /**
     * 用户注册
     * @param person 用户保存在数据库中的基本信息
     * @param part 用户的头像图片上传保存在服务端
     * @param smsCode 用户的手机短信验证码
     * @param studentPassword 学生的教务系统密码
     * @return
     */
    @PostMapping("/a/register")
    public Result register(@Valid Person person,
                           @RequestPart("file") Part part,
                           @RequestParam String smsCode,
                           @RequestParam String studentPassword,
                           HttpSession session) throws LoginException, PublicKeyException, cc.yyf.book.zfjw.exception.LoginException {
        log.info("*****smsCode : " + smsCode);
        log.info("*****smsCode : " + studentPassword);
        log.info("*****person : " + person);
        // 雪花算法
        SnowFlake snowFlake = new SnowFlake(2, 3);
        String playSrcFile = snowFlake.nextId() + ".jpg";
        person.setHeadSrc(playSrcFile);
        String src = session.getServletContext().getRealPath("/");
        // 存储图片的路径
        src += playSrcFile;
        return registerService.register(person, smsCode, studentPassword, src, part);
    }
}
