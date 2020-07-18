package cc.yyf.book.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  OrderStatus {
    /**
     * 代付款
     */
    WAIT_PAY(1, "等待支付"),

    /**
     * 支付完成
     */
    SUCCESS_PAY(2, "成功支付"),

    /**
     * 已取消
     */
    CANCEL(3, "订单已取消"),

    /**
     * 已过期
     */
    END_TIME(4, "已过期"),

    /**
     * 订单已完成
     */
    OK(5, "已完成");

    int status;

    String message;

    OrderStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
