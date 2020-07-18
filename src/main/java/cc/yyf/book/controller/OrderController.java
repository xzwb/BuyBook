package cc.yyf.book.controller;

import cc.yyf.book.pojo.BuyCarAdd;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
