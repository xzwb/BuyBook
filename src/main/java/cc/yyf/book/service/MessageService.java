package cc.yyf.book.service;

import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Result;

public interface MessageService {
    /**
     * 查看卖家需要发货的商品
     * @param studentCode
     * @param page
     * @return
     */
    Result getMessage(Page page, String studentCode);

    /**
     * 查看我已经卖出的书籍
     * @param page
     * @param studentCode
     * @return
     */
    Result getSell(Page page, String studentCode);
}
