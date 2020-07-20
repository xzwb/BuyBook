package cc.yyf.book.controller;

import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/u/add/buyCar")
    public Result addBuyCar(@RequestBody BuyCarAdd buyCarAdd,
                            HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        buyCarAdd.setAddTime(new Date());
        buyCarAdd.setStudentCode(studentCode);
        return orderService.addBuyCar(buyCarAdd);
    }

    /**
     * 获取自己购物车的内容
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/u/search/buyCar")
    public Result searchCar(@RequestBody Page page,
                            HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.searchBuyCar(page, studentCode);
    }

    /**
     * 删除购物车中的商品(一件)
     * @param buyCarId
     * @param request
     * @return
     */
    @PostMapping("/u/delete/buyCar/{buyCarId}")
    public Result deleteBuyCar(@PathVariable("buyCarId") int buyCarId,
                               HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return orderService.deleteBuyCar(studentCode, buyCarId);
    }
}
