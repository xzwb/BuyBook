package cc.yyf.book.service;

import cc.yyf.book.pojo.*;

import java.io.IOException;
import java.text.ParseException;
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
     * 取消一个待支付的订单
     * @param studentCode 学号
     * @param orderId 订单号
     * @return
     */
    Result cancelOrder(String studentCode, int orderId);

    /**
     * 支付一个待支付的订单
     * @param orderId
     * @return
     */
    Result payOrder(int orderId);


    /**
     * 生成订单
     * @param makeOrder
     * @param studentCode
     * @return
     */
    Result makeOrder(MakeOrder makeOrder, String studentCode) throws IOException, ParseException;

    /**
     * 立即支付订单
     * @param orderIds 订单列表
     * @return
     */
    Result payOrderNow(List<Integer> orderIds);
}
