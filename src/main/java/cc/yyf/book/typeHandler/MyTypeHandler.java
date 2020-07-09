package cc.yyf.book.typeHandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * mybatis自定义java类型和数据库类型的转换
 * list 和 varchar的转换
 */
public class MyTypeHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        String list = String.join(",", strings);
        preparedStatement.setString(i, list);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        List<String> list = Arrays.asList(result.split(","));
        return list;
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        List<String> list = Arrays.asList(result.split(","));
        return list;
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        List<String> list = Arrays.asList(result.split(","));
        return list;
    }
}
