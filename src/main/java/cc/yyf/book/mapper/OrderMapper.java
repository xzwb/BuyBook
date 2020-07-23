package cc.yyf.book.mapper;

import cc.yyf.book.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface OrderMapper {


    /**
     * 添加订单
     */
    void insertBookOrder(UserOrder userOrder);

    /**
     * 获取订单信息
     * @param studentCode
     * @param from
     * @param size
     * @return
     */
    List<UserOrder> searchOrder(@Param("studentCode") String studentCode,
                                @Param("from") int from,
                                @Param("size") int size);

    /**
     * 根据订单类别查看订单
     * @param studentCode
     * @param status
     * @param from
     * @param size
     * @return
     */
    List<UserOrder> searchOrderByStyle(@Param("studentCode") String studentCode,
                                @Param("status") int status,
                                @Param("from") int from,
                                @Param("size") int size);

    /**
     * 直接从商品主页付款
     * @param userOrder
     */
    void buyBook(UserOrder userOrder);

    /**
     * 取消一个待支付的订单
     * @param studentCode 学号
     * @param orderId 订单号
     * @param oldStatus 以前的订单状态为待支付
     * @param newStatus 新的订单状态为已取消
     */
    void cancelOrder(@Param("studentCode") String studentCode,
                     @Param("orderId") int orderId,
                     @Param("oldStatus")OrderStatus oldStatus,
                     @Param("newStatus")OrderStatus newStatus);

    /**
     * 通过订单号来获取bookId
     * @param orderId
     * @return
     */
    int getBookIdByOrderId(@Param("orderId") int orderId);


}
