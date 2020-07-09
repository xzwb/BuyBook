package cc.yyf.book.mapper;

import cc.yyf.book.pojo.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    /**
     * 将用户信息插入数据库
     * @param person
     */
    void insertPerson(Person person);
}
