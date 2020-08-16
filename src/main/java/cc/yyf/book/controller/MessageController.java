package cc.yyf.book.controller;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 卖家查看的消息
 */
@RestController
@CrossOrigin(allowCredentials = "true")
public class MessageController {

    @Autowired
    MessageService messageService;

    /**
     * 查看我即将要发货的书籍
     * @param request
     * @param page
     * @return
     */
    @GetMapping("/u/search/message")
    public Result lookMessage(@RequestBody Page page,
                              HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return messageService.getMessage(page, studentCode);
    }

    /**
     * 查看我成功卖出的书籍
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/u/search/sell")
    public Result getSell(@RequestBody Page page,
                          HttpServletRequest request) {
        String studentCode = (String) request.getAttribute("studentCode");
        return messageService.getSell(page, studentCode);
    }
}
