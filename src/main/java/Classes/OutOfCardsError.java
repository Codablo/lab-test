package Classes;

/**
 * Created by mikehollibaugh on 11/5/16.
 */
public class OutOfCardsError extends Exception {
    public OutOfCardsError() {
    }

    public OutOfCardsError(String message) {
        super(message);
    }

    public OutOfCardsError(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfCardsError(Throwable cause) {
        super(cause);
    }

    public OutOfCardsError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
