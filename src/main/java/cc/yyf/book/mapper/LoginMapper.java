package cc.yyf.book.mapper;

import cc.yyf.book.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    /**
     * 登录
     * @param studentCode
     * @param password
     * @return
     */
    Person loginSelect(@Param("studentCode") String studentCode,
                       @Param("password") String password);
}
