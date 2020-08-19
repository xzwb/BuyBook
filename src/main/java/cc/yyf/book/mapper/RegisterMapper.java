package cc.yyf.book.mapper;

import cc.yyf.book.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegisterMapper {
    /**
     * 将用户信息插入数据库
     * @param person
     */
    void insertPerson(Person person);


}
