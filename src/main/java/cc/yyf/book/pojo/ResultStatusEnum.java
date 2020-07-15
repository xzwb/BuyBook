package cc.yyf.book.pojo;

public enum ResultStatusEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 服务器异常
     */
    EXCEPTION(500, "服务器异常"),

    /**
     * 文档查询失败
     */
    DOC_NOT_FOUND(-2, "没有找到该书籍的文档"),

    /**
     * 传入的电话号码为空
     */
    NULL_EXCEPTION(500, "传入的参数为空"),

    /**
     * 保存图片文件时错误
     */
    IO_EXCEPTION(500, "保存图片文件错误"),

    /**
     * 正方教务系统登录失败
     */
    ZFJW_FALSE(-3, "教务系统验证错误"),

    /**
     * 短信验证码错误
     */
    SMS_CODE_MISTAKE(-4, "短信验证码错误"),

    /**
     * 学号已经被注册
     */
    CODE_HAVE(-5, "该学号已经被注册"),

    /**
     * 获取不到token
     */
    TOKEN_FALSE(-1, "长时间未操作请重新登录"),

    /**
     * token更新
     */
    TOKEN_CHANGE(201, "token更新了"),

    /**
     * 用户名或者密码错误
     */
    LOGIN_FALSE(-6, "用户名或者密码错误"),

    /**
     * 该商品没货了
     */
    NOT_HAVE_STOCK(-7, "货物存量不足"),

    /**
     * 请求错误
     */
    BIND_EXCEPTION(400);

    private int status;
    private String message;

    ResultStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ResultStatusEnum(int status) {
        this.status = status;
    }

    public static ResultStatusEnum BIND_EXCEPTION(String errMsg) {
        BIND_EXCEPTION.message = errMsg;
        return BIND_EXCEPTION;
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
