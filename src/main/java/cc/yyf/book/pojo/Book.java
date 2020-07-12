package cc.yyf.book.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Book implements Serializable {
    // 书籍名称
    @NotNull
    private String bookName;
    // 书籍编号
    private int bookId;
    // 发布者的学号
    private String studentCode;
    // 发布者的昵称
    private String userName;
    // 书的类型
    private List<String> bookStyle = new ArrayList<>();
    // 书的简介
    @NotNull
    private String bookIntroduction;
    // 书的图片路径
    private String bookSrc;
    // 发布的日期
    private Date bookDate;
    // 书的价格
    @NotNull
    private float price;
    // 库存
    @NotNull
    private int stock;
}
