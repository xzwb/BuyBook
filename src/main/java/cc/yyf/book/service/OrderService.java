package cc.yyf.book.service;

import cc.yyf.book.pojo.*;

import java.io.IOException;
import java.util.List;


public interface OrderService {

    /**
     * 添加商品到购物车
     * @param buyCar
     * @param studentCode
     * @return
     */
    Result addBuyCar(BuyCar buyCar, String studentCode);


    /**
     * 获取自己的购物车信息
     * @param page
     * @param studentCode
     * @return
     */
    Result searchBuyCar(Page page, String studentCode) throws IOException;

    /**
     * 删除购物车中的一件商品
     * @param studentCode 学号
     * @param bookIds 购物车中商品的编号
     * @return
     */
    Result deleteBuyCar(String studentCode, List<Integer> bookIds);

    /**
     * 保存订单
     * @param userOrder
     * @return
     */
    Result saveBookOrder(UserOrder userOrder);

    /**
     * 查看所有的订单
     * @param studentCode
     * @param from
     * @param page
     * @return
     */
    Result searchOrder(String studentCode, int from, int page);

    /**
     * 按照订单类别查询
     * @param studentCode
     * @param status
     * @param from
     * @param size
     * @return
     */
    Result searchOrderByStyle(String studentCode, int status, int from, int size);

    /**
     * 用户直接从商品主页购买
     * @param userOrder
     * @return
     */
    Result buyBook(UserOrder userOrder);

    /**
     * 取消一个待支付的订单
     * @param studentCode 学号
     * @param orderId 订单号
     * @return
     */
    Result cancelOrder(String studentCode, int orderId);

    /**
     * 支付一个待支付的订单
     * @param studentCode
     * @param orderId
     * @return
     */
    Result payOrder(String studentCode, int orderId);

//    /**
//     * 从购物车中支付
//     * @param studentCode
//     * @param buyCarIds
//     * @return
//     */
//    Result payBuyCar(String studentCode, List<Integer> buyCarIds);
}
