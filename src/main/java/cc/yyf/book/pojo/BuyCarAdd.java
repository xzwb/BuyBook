package cc.yyf.book.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 添加到购物车时候的类
 */
@Data
@Alias("buyCarAdd")
public class BuyCarAdd {
    // 要添加到购物车的书籍编号
    private int bookId;
    // 买家的学号
    private String studentCode;
    // 添加的时间
    private Date addTime;
}
