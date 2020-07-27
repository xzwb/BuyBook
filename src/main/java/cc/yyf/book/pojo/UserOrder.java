package cc.yyf.book.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户的订单类
 */
@Alias("userOrder")
@Data
public class UserOrder {
    // 订单的唯一编号
    private int orderId;
    // 买家学号
    private String studentCode;
    // 商品编号
    private int bookId;
    // 购买的时间
    private Date buyDate;
    // 订单状态
    private OrderStatus orderStatus;
    // 订单结束时间
    private Date orderEndTime;
    // 购买数量
    private int number;

    // 书籍的详细信息
    private Book book;

    public static UserOrder build(String studentCode, int bookId, Date buyDate, OrderStatus orderStatus, Date orderEndTime, int number) {
        UserOrder userOrder = new UserOrder();
        userOrder.setStudentCode(studentCode);
        userOrder.setBookId(bookId);
        userOrder.setBuyDate(buyDate);
        userOrder.setOrderStatus(orderStatus);
        userOrder.setOrderEndTime(orderEndTime);
        userOrder.setNumber(number);
        return userOrder;
    }
}
