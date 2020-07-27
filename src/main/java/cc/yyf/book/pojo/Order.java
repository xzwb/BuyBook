package cc.yyf.book.pojo;

import cc.yyf.book.util.SnowFlake;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单类
 */
@Data
@Alias("order")
public class Order {
    // 订单号
    private int orderId;
    // 书籍编号
    private int bookId;
    // 书的名字
    private String bookName;
    // 卖家学号
    private String sellerStudentCode;
    // 卖家昵称
    private String sellerName;
    // 书籍图片
    private String bookSrc;
    // 书籍价格
    private Double price;
    // 购买的数量
    private int number;

    /**
     * 传入书籍类，构造订单类
     * @param book
     * @param number 购买的数量
     * @return
     */
    public static Order build(Book book, int number) {
        Order order = new Order();
        order.setNumber(number);
        order.setBookId(book.getBookId());
        order.setBookSrc(book.getBookSrc());
        order.setBookName(book.getBookName());
        order.setSellerName(book.getUserName());
        order.setSellerStudentCode(book.getStudentCode());
        order.setPrice(book.getPrice());
        return order;
    }
}
