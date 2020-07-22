package cc.yyf.book.listen;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class SmsCodeListen {
    @Autowired
    RedisTemplate redisTemplate;

    private final int AppID = 1400341372;

    private final String AppKey = "4f64c973f40e450534016ce13709dac0";

    private final int TemplateId = 565992;

    private final String SmsSign = "大小胖几的日常";

    /**
     * 发短信
     * @param phoneNumber
     */
    @RabbitListener(queues = "queue_sms_yyf")
    public void sendSmsCode(String phoneNumber) throws HTTPException, IOException {
        String smsCode = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            smsCode += random.nextInt(10);
        }
        // 短信中的变量
        String[] params = {smsCode, "5"};
        SmsSingleSender sender = new SmsSingleSender(AppID, AppKey);
        SmsSingleSenderResult result = sender.sendWithParam("86",
                phoneNumber, TemplateId, params, SmsSign, "", "");
        if (result.result == 0) {
            /**
             * 如果短信发送成功把得到的短信验证码存到redis中
             * 并设置6分钟过期
             */
            redisTemplate.opsForValue().set(phoneNumber, smsCode, 360, TimeUnit.SECONDS);
        }
    }
}

