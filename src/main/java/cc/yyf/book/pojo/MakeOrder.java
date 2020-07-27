package cc.yyf.book.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存订单时候的类
 */
@Data
public class MakeOrder {
    // 书籍编号
    private List<Integer> bookIds = new ArrayList<>();
    // 购买的数量
    private List<Integer> number = new ArrayList<>();
}
