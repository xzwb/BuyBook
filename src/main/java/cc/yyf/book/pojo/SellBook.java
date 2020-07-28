package cc.yyf.book.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 卖家查看自己要发货的东西
 */
@Data
@Alias("sellBook")
public class SellBook {
    // 买家学号
    private String buyerStudentCode;
    // 买家昵称
    private String buyerName;
    // 书籍名称
    private String bookName;
    // 书籍图片
    private String bookSrc;
    // 书籍购买时间
    private Date buyDate;
    // 书籍购买数量
    private int buyNumber;
}
