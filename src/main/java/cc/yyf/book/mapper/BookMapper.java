package cc.yyf.book.mapper;

import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    /**
     * 添加书籍
     * @param book
     */
    void insertBook(Book book);

    /**
     * 库存减
     * @param bookId
     * @param num 库存减少的数量
     */
    void subStock(@Param("bookId") int bookId,
                  @Param("num") int num);

    /**
     * 增加库存
     */
    void addStock(@Param("bookId") int bookId,
                  @Param("num") int num);


    /**
     * 获取书籍存货
     * @param bookId
     * @return
     */
    int getStock(@Param("bookId") int bookId);

    /**
     * 找到要取消的订单
     */
    List<Integer> selectInvalidOrder(@Param("time") String time,
                                     @Param("status") OrderStatus status);

    /**
     * 取消过期订单
     */
    void orderJobMapper(@Param("time") String time,
                        @Param("oldStatus") OrderStatus oldStatus,
                        @Param("newStatus") OrderStatus newStatus);

    /**
     * 获取书籍总数
     */
    int getBookTotal();
}
