package cc.yyf.book.controller;

import cc.yyf.book.pojo.*;
import cc.yyf.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 添加购物车
     * @param request
     * @return
     */
    @PostMapping("/u/add/buyCar")
    public Result addBuyCar(@RequestBody BuyCar buyCar,
                            HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.addBuyCar(buyCar, studentCode);
    }

    /**
     * 获取自己购物车的内容
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/u/search/buyCar")
    public Result searchCar(@RequestBody Page page,
                            HttpServletRequest request) throws IOException {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.searchBuyCar(page, studentCode);
    }


    /**
     * 删除购物车中书籍
     * @param bookIds
     * @return
     */
    @PostMapping("/u/delete/buyCar")
    public Result deleteBuyCar(@RequestBody List<Integer> bookIds,
                               HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.deleteBuyCar(studentCode, bookIds);
    }

    /**
     * 从商品主页保存订单
     * @param bookId
     * @param request
     * @return
     */
    @PostMapping("/u/save/order/{bookId}")
    public Result buyBook(@PathVariable("bookId") int bookId,
                          HttpServletRequest request) {
        Date buyDate = new Date();
        String studentCode = (String) request.getAttribute("studentCode");
        // 设置订单过期时间15分钟
        Date orderEndTime = new Date(buyDate.getTime() + 15*1000*60);
        UserOrder userOrder = UserOrder.build(studentCode, bookId, buyDate, OrderStatus.WAIT_PAY, orderEndTime);
        return orderService.saveBookOrder(userOrder);
    }

    /**
     * 获取用户自己所有订单信息
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/u/search/order")
    public Result searchOrder(@Valid @RequestBody Page page,
                              HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        return orderService.searchOrder(studentCode, from, size);
    }

    /**
     * 按照订单条件查看订单
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/u/search/order/style")
    public Result searchOrders(@Valid @RequestBody Page page,
                               HttpServletRequest request) {
        int orderStatus = Integer.parseInt(page.getMessage());
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.searchOrderByStyle(studentCode, orderStatus, from, size);
    }

    /**
     * 从商品主页直接购买
     * @param bookId
     * @param request
     * @return
     */
    @PostMapping("/u/buy/book/{bookId}")
    public Result buyBookPay(@PathVariable("bookId") int bookId,
                          HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        Date date = new Date();
        UserOrder userOrder = UserOrder.build(studentCode, bookId, date, OrderStatus.SUCCESS_PAY, null);
        return orderService.buyBook(userOrder);
    }

    /**
     * 取消一个订单
     * @param orderId
     * @return
     */
    @PostMapping("/u/cancel/{orderId}")
    public Result cancelOrder(@PathVariable("orderId") int orderId,
                              HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.cancelOrder(studentCode, orderId);
    }


    /**
     * 从订单页支付一个待支付的订单
     * @param orderId
     * @param request
     * @return
     */
    @PostMapping("/u/pay/order/{orderId}")
    public Result payOrder(@PathVariable("orderId")int orderId,
                           HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.payOrder(studentCode, orderId);
    }

}
