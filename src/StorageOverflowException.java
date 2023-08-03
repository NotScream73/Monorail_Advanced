import java.io.Serializable;

public class StorageOverflowException extends Exception implements Serializable {
    public StorageOverflowException() {
    }

    public StorageOverflowException(int count) {
        super("В наборе превышено допустимое количество: " + count);
    }

    public StorageOverflowException(String message) {
        super(message);
    }

    public StorageOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageOverflowException(Throwable cause) {
        super(cause);
    }

    public StorageOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
