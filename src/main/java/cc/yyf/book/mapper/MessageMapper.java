package cc.yyf.book.mapper;

import cc.yyf.book.pojo.OrderStatus;
import cc.yyf.book.pojo.SellBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 获取卖出的书籍信息
     * @param from
     * @param size
     * @param status
     * @param studentCode
     * @return
     */
    List<SellBook> getSellBook(@Param("from") int from,
                               @Param("size") int size,
                               @Param("status")OrderStatus status,
                               @Param("studentCode") String studentCode);

    /**
     * 获取卖出的书籍总数
     * @param status
     * @param studentCode
     * @return
     */
    int getSellBookTotal(@Param("status")OrderStatus status,
                         @Param("studentCode") String studentCode);

}
