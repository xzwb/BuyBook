package cc.yyf.book.mapper;

import cc.yyf.book.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询用户
     * @param from
     * @param size
     * @param message
     * @return
     */
    List<Person> selectPerson(@Param("from") int from,
                              @Param("size") int size,
                              @Param("message") String message);
}
