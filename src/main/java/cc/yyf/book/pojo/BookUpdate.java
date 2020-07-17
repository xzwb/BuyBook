package cc.yyf.book.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改书籍信息时用到的类
 * 以下字段都是可以修改的字段
 */
@Data
public class BookUpdate {
    // 书籍编号
    private int bookId;
    // 书籍名称
    private String bookName;
    // 书籍简介
    private String bookIntroduction;
    // 书籍类型
    private List<String> bookStyle = new ArrayList<>();
    // 书籍价格
    private Double price;
}
