package cc.yyf.book.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 注册时接受的用户信息
 */
// 设置mybatis别名
@Alias("person")
@Data
public class Person {

    String headSrc;

    @NotNull
    // 学号为8位数字
    @Pattern(regexp = "^\\d{8}$", message = "学号格式错误")
    String studentCode;

    @NotNull
    String password;

    @NotNull
    String userName;

    @NotNull
    @Pattern(regexp = "^1([34578])\\d{9}$",message = "手机号码格式错误")
    String phoneNumber;
}

