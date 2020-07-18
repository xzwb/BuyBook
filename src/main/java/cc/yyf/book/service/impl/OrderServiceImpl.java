package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.OrderMapper;
import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    /**
     * 添加商品到购物车
     * @param buyCarAdd
     * @return
     */
    @Override
    @Transactional
    public Result addBuyCar(BuyCarAdd buyCarAdd) {
        orderMapper.insertBuyCar(buyCarAdd);
        return Result.build(ResultStatusEnum.SUCCESS);
    }
}
