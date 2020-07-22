package cc.yyf.book.service;

import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.UserOrder;

public interface OrderService {
    /**
     * 添加商品到购物车
     * @param buyCarAdd
     * @return
     */
    Result addBuyCar(BuyCarAdd buyCarAdd);

    /**
     * 获取自己的购物车信息
     * @param page
     * @param studentCode
     * @return
     */
    Result searchBuyCar(Page page, String studentCode);

    /**
     * 删除购物车中的一件商品
     * @param studentCode 学号
     * @param buyCarId 购物车中商品的编号
     * @return
     */
    Result deleteBuyCar(String studentCode, int buyCarId);

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
}
