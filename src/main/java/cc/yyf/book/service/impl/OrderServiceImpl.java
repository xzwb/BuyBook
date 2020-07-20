package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.OrderMapper;
import cc.yyf.book.pojo.*;
import cc.yyf.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取购物车中的内容
     * @param page
     * @param studentCode
     * @return
     */
    @Override
    @Transactional
    public Result searchBuyCar(Page page, String studentCode) {
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        List<BuyCarSelect> list = new ArrayList<>();
        list = orderMapper.searchBuyCar(from, size, studentCode);
        return Result.build(ResultStatusEnum.SUCCESS, list);
    }

    /**
     * 从购物车中删除一件商品
     * @param studentCode 学号
     * @param buyCarId 购物车中商品的编号
     * @return
     */
    @Transactional
    @Override
    public Result deleteBuyCar(String studentCode, int buyCarId) {
        orderMapper.deleteBuyCar(studentCode, buyCarId);
        return Result.build(ResultStatusEnum.SUCCESS);
    }
}
