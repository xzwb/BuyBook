package cc.yyf.book.mapper;

import cc.yyf.book.pojo.BookUpdate;
import cc.yyf.book.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HomeMapper {
    /**
     * 根据学号查询用户信息
     * @param studentCode 学号
     * @return
     */
    Person selectPersonByStudentCode(@Param("studentCode") String studentCode);

    /**
     * 修改书籍信息
     * @param studentCode
     * @param book
     */
    void updateBook(@Param("studentCode") String studentCode,
                    @Param("book")BookUpdate book);
}
