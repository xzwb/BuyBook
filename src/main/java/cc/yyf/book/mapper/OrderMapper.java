package cc.yyf.book.mapper;

import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.BuyCarSelect;
import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 添加商品到购物车
     * @param buyCarAdd
     */
    void insertBuyCar(BuyCarAdd buyCarAdd);

    /**
     * 获取购物车信息
     * @param from
     * @param size
     * @param studentCode
     * @return
     */
    List<BuyCarSelect> searchBuyCar(@Param("from")int from,
                                    @Param("size")int size,
                                    @Param("studentCode") String studentCode);

    /**
     * 删除购物车中的一件商品
     * @param studentCode 学号
     * @param buyCarId 购物车中商品的编号
     */
    void deleteBuyCar(@Param("studentCode")String studentCode,
                      @Param("buyCarId") int buyCarId);


    /**
     * 添加订单
     */
    void insertBookOrder(UserOrder userOrder);
}