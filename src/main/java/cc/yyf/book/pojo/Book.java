package cc.yyf.book.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Double price;
    // 库存
    @NotNull
    private int stock;

    /**
     * 构造者模式传入es上取出的map，构造成book对象
     * @param map
     * @return
     */
    public static Book build(Map<String, Object> map) throws ParseException {
        Book book = new Book();
        book.setBookSrc((String) map.get("bookSrc"));
        // 字符串转化为时间日期类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = (String) map.get("bookDate");
        book.setBookDate(simpleDateFormat.parse(date.substring(0, 10)));

        book.setStudentCode((String) map.get("studentCode"));
        book.setBookId((Integer) map.get("bookId"));
        book.setBookName((String) map.get("bookName"));
        book.setBookIntroduction((String) map.get("bookIntroduction"));
        book.setPrice((Double)map.get("price"));
        book.setUserName((String) map.get("userName"));
        book.setStock((Integer) map.get("stock"));
        String bookStyle = (String) map.get("bookStyle");
        book.setBookStyle(Arrays.asList(bookStyle.split(",")));

        return book;
    }
}
