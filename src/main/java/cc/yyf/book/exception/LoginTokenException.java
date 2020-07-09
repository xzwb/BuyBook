package cc.yyf.book.exception;

/**
 * 验证token信息时会遇到的异常
 */
public class LoginTokenException extends RuntimeException {
    public LoginTokenException() {
        super();
    }

    public LoginTokenException(String message) {
        super(message);
    }

    public LoginTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginTokenException(Throwable cause) {
        super(cause);
    }

    protected LoginTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
