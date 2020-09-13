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

    /**
     * 根据学号查询用户
     * @param studentCode
     * @return
     */
    Person getPersonByStudentCode(@Param("studentCode") String studentCode);

    /**
     * 查询用户
     * @param message
     * @return
     */
    int getPersonTotal(@Param("message") String message);
}
