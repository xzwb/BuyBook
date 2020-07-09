package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.RegisterMapper;
import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.RegisterService;
import cc.yyf.book.thread.SaveFileThread;
import cc.yyf.book.zfjw.exception.PublicKeyException;
import cc.yyf.book.zfjw.model.LoginStatus;
import cc.yyf.book.zfjw.model.User;
import cc.yyf.book.zfjw.service.LoginService;
import cc.yyf.book.zfjw.service.impl.LoginServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Part;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    RegisterMapper registerMapper;

    /**
     * redis
     */
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * rabbitmq延迟队列
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 使用延迟队列发送手机短信验证码
     * @param phoneNumber 用户手机号
     * @return
     */
    @Override
    public Result getSMSCode(String phoneNumber) {
        rabbitTemplate.convertAndSend("exchange_sms_yyf", "sms", phoneNumber);
        return  Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     *
     * @param person 需要保存到数据库中的用户信息
     * @param smsCode 短信验证码
     * @param studentPassword 学生教务系统密码
     * @param src 图片路径
     * @param part Part
     * @return
     * @throws LoginException
     * @throws PublicKeyException
     * @throws cc.yyf.book.zfjw.exception.LoginException
     */
    @Override
    public Result register(Person person, String smsCode, String studentPassword, String src, Part part) throws LoginException, PublicKeyException, cc.yyf.book.zfjw.exception.LoginException {
        // 用户密码使用md5加密后保存到数据库
        person.setPassword(DigestUtils.md5DigestAsHex(person.getPassword().getBytes()));
        if (smsCodeService(person.getPhoneNumber(), smsCode)) {
            if (ZFJWService(person.getStudentCode(), studentPassword)) {
                registerMapper.insertPerson(person);
                // 使用异步保存头像文件
                System.out.println(src);
                new SaveFileThread(part, src).run();

                return Result.build(ResultStatusEnum.SUCCESS);
            } else {
                return Result.build(ResultStatusEnum.ZFJW_FALSE);
            }
        } else {
            return Result.build(ResultStatusEnum.SMS_CODE_MISTAKE);
        }
    }

    /**
     * 验证短信验证码
     * @param phoneNumber 手机号
     * @param smsCode 短信验证码
     * @return
     */
    private Boolean smsCodeService(String phoneNumber, String smsCode) {
        // 如果短信验证码和redis中的一致就删除redis中的验证码
        if (smsCode.equals(redisTemplate.opsForValue().get(phoneNumber))) {
            redisTemplate.delete(phoneNumber);
            return true;
        }
        return false;
    }
    /**
     * 验证是不是本校学生
     * @param code 学号
     * @param password 教务系统密码
     * @return
     * @throws LoginException
     * @throws PublicKeyException
     * @throws cc.yyf.book.zfjw.exception.LoginException
     */
    private Boolean ZFJWService(String code, String password) throws LoginException, PublicKeyException, cc.yyf.book.zfjw.exception.LoginException, cc.yyf.book.zfjw.exception.LoginException {
        User user = User.builder(code, password);
        LoginService loginService = new LoginServiceImpl("www.zfjw.xupt.edu.cn", user);
        LoginStatus loginStatus = loginService.login();
        if (!loginStatus.isSuccess()) {
            return true;
        }
        return false;
    }
}
