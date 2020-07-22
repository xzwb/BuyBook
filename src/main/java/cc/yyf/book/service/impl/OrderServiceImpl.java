package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.BookMapper;
import cc.yyf.book.mapper.OrderMapper;
import cc.yyf.book.pojo.*;
import cc.yyf.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    BookMapper bookMapper;

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

    /**
     * 保存订单
     * @param userOrder
     * @return
     */
    @Override
    @Transactional
    public Result saveBookOrder(UserOrder userOrder) {
        // 查查库存
        if (haveBook(userOrder.getBookId())) {
            orderMapper.insertBookOrder(userOrder);
            bookMapper.supStock(userOrder.getBookId());
            return Result.build(ResultStatusEnum.SUCCESS);
        }
        return Result.build(ResultStatusEnum.NOT_HAVE_STOCK);
    }

    /**
     * 查看所有的订单
     * @param studentCode
     * @param from
     * @param page
     * @return
     */
    @Override
    public Result searchOrder(String studentCode, int from, int page) {
        List<UserOrder> list = new ArrayList<>();
        list = orderMapper.searchOrder(studentCode, from, page);
        return Result.build(ResultStatusEnum.SUCCESS, list);
    }

    /**
     * 按照订单类别查询
     * @param studentCode
     * @param status
     * @param from
     * @param size
     * @return
     */
    @Override
    public Result searchOrderByStyle(String studentCode, int status, int from, int size) {
        List<UserOrder> list = new ArrayList<>();
        list = orderMapper.searchOrderByStyle(studentCode, status, from, size);
        return Result.build(ResultStatusEnum.SUCCESS, list);
    }

    /**
     * 用户直接从商品主页付款
     * @param userOrder
     * @return
     */
    @Override
    @Transactional
    public Result buyBook(UserOrder userOrder) {
        orderMapper.buyBook(userOrder);
        bookMapper.supStock(userOrder.getBookId());
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 取消一个待支付的订单
     * @param studentCode 学号
     * @param orderId 订单号
     * @return
     */
    @Override
    @Transactional
    public Result cancelOrder(String studentCode, int orderId) {
        orderMapper.cancelOrder(studentCode, orderId, OrderStatus.WAIT_PAY, OrderStatus.CANCEL);
        int bookId = orderMapper.getBookIdByOrderId(orderId);
        bookMapper.addStock(bookId);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 检测是否有货
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean haveBook(int bookId) {
        int stock = bookMapper.getStock(bookId);
        if (stock > 0) {
            return true;
        }
        return false;
    }
}
