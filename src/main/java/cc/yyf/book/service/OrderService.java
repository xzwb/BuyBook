package cc.yyf.book.service;

import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;

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
}
