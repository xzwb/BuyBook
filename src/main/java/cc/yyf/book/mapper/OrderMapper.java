package cc.yyf.book.mapper;

import cc.yyf.book.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


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
     * 支付订单
     * @param status
     * @param orderId
     */
    void payOrder(@Param("status")OrderStatus status,
                  @Param("orderId")int orderId);

    /**
     * 通过订单号来获取bookId
     * @param orderId
     * @return
     */
    int getBookIdByOrderId(@Param("orderId") int orderId);

    /**
     * 获取订单状态
     */
    int getOrderStatus(@Param("orderId") int orderId);

    /**
     * 通过订单号查询该订单买了多少个
     * @param orderId
     * @return
     */
    int getNumByOrderId(@Param("orderId") int orderId);


    /**
     * 根据orderId获取订单信息
     */
    Order getOrderByOrderId(@Param("orderId") int orderId);



}
