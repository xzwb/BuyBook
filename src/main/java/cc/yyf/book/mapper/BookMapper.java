package cc.yyf.book.mapper;

import cc.yyf.book.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    /**
     * 添加书籍
     * @param book
     */
    void insertBook(Book book);
}
