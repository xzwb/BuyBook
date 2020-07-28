package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.MessageMapper;
import cc.yyf.book.pojo.*;
import cc.yyf.book.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    /**
     * 卖家查看他将要发货的东西
     * @param studentCode
     * @param page
     * @return
     */
    @Override
    public Result getMessage(Page page, String studentCode) {
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        List<SellBook> sellBook = messageMapper.getSellBook(from, size, OrderStatus.SUCCESS_PAY, studentCode);
        return Result.build(ResultStatusEnum.SUCCESS, sellBook);
    }

    /**
     * 查看已经完成的订单
     * @param page
     * @param studentCode
     * @return
     */
    @Override
    public Result getSell(Page page, String studentCode) {
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        List<SellBook> sellBook = messageMapper.getSellBook(from, size, OrderStatus.OK, studentCode);
        return Result.build(ResultStatusEnum.SUCCESS, sellBook);
    }
}
