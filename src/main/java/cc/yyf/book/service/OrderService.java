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
}
