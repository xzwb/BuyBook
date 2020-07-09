package cc.yyf.book.zfjw.model;

public class LoginStatus {
    // 登录信息
    private final boolean success;

    private final String errorMsg;

    private static final String DEFAULT_MSG = "login failed";

    private LoginStatus(boolean success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public static LoginStatus success() {
        return new LoginStatus(false, null);
    }

    public static LoginStatus error(String msg) {
        if (msg == null) {
            msg = DEFAULT_MSG;
        }
        return new LoginStatus(true, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static String getDefaultMsg() {
        return DEFAULT_MSG;
    }
}
