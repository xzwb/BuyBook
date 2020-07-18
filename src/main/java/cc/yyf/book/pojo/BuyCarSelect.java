package cc.yyf.book.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 查询购物车信息时用到的类
 */
@Data
public class BuyCarSelect {
    // 购物车编号
    private int buyCarId;
    // 添加时间
    private Date addTime;
    // 添加的书籍的详细信息
    private Book book;
}
