package cc.yyf.book.exception;

public class BuyCarException extends RuntimeException {
    public BuyCarException() {
    }

    public BuyCarException(String message) {
        super(message);
    }

    public BuyCarException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuyCarException(Throwable cause) {
        super(cause);
    }

    public BuyCarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
