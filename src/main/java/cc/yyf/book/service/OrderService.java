package cc.yyf.book.service;

import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.Result;

public interface OrderService {
    /**
     * 添加商品到购物车
     * @param buyCarAdd
     * @return
     */
    Result addBuyCar(BuyCarAdd buyCarAdd);
}
