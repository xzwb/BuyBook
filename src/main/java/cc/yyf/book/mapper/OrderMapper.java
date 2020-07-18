package cc.yyf.book.mapper;

import cc.yyf.book.pojo.BuyCarAdd;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    /**
     * 添加商品到购物车
     * @param buyCarAdd
     */
    void insertBuyCar(BuyCarAdd buyCarAdd);
}
