package cc.yyf.book.typeHandler;

import cc.yyf.book.pojo.OrderStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 自定义枚举类在数据库中的存储
 */
public class OrderEnumTypeHandler implements TypeHandler<OrderStatus> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, OrderStatus orderStatus, JdbcType jdbcType) throws SQLException {
        int status = orderStatus.getStatus();
        preparedStatement.setInt(i, status);
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, String s) throws SQLException {
        int status = resultSet.getInt(s);
        if (status == 1) {
            return OrderStatus.WAIT_PAY;
        } else if (status == 2) {
            return OrderStatus.SUCCESS_PAY;
        } else if (status == 3) {
            return OrderStatus.CANCEL;
        } else if (status == 4) {
            return OrderStatus.END_TIME;
        } else {
            return OrderStatus.OK;
        }
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, int i) throws SQLException {
        int status = resultSet.getInt(i);
        if (status == 1) {
            return OrderStatus.WAIT_PAY;
        } else if (status == 2) {
            return OrderStatus.SUCCESS_PAY;
        } else if (status == 3) {
            return OrderStatus.CANCEL;
        } else if (status == 4) {
            return OrderStatus.END_TIME;
        } else {
            return OrderStatus.OK;
        }
    }

    @Override
    public OrderStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        int status = callableStatement.getInt(i);
        if (status == 1) {
            return OrderStatus.WAIT_PAY;
        } else if (status == 2) {
            return OrderStatus.SUCCESS_PAY;
        } else if (status == 3) {
            return OrderStatus.CANCEL;
        } else if (status == 4) {
            return OrderStatus.END_TIME;
        } else {
            return OrderStatus.OK;
        }
    }
}

