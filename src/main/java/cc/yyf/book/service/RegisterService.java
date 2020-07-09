package cc.yyf.book.service;

import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.zfjw.exception.PublicKeyException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Part;


public interface RegisterService {

    /**
     * 获取短信验证码
     * @param phoneNumber 用户手机号
     * @return
     */
    Result getSMSCode(String phoneNumber);

    /**
     * 注册
     * @param person 需要保存到数据库中的用户信息
     * @param smsCode 短信验证码
     * @param studentPassword 学生教务系统密码
     * @param src 图片路径
     * @param part Part
     * @return
     */
    Result register(Person person,
                    String smsCode,
                    String studentPassword,
                    String src,
                    Part part) throws LoginException, PublicKeyException, cc.yyf.book.zfjw.exception.LoginException;
}
