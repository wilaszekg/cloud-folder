package pl.cloudfolder.infrastructure.dropbox.clients;

public class DropboxException extends RuntimeException {
    public DropboxException() {
    }

    public DropboxException(String message) {
        super(message);
    }

    public DropboxException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropboxException(Throwable cause) {
        super(cause);
    }

    public DropboxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
