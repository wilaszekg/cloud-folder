package pl.cloudfolder.infrastructure.google.clients;

public class GoogleException extends RuntimeException {
    public GoogleException() {
    }

    public GoogleException(String message) {
        super(message);
    }

    public GoogleException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoogleException(Throwable cause) {
        super(cause);
    }

    public GoogleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
