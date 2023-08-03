import java.io.Serializable;

public class LocomotiveNotFoundException extends Exception implements Serializable {
    public LocomotiveNotFoundException() {
    }

    public LocomotiveNotFoundException(int i) {
        super("Не найден объект по позиции " + i);
    }

    public LocomotiveNotFoundException(String message) {
        super(message);
    }

    public LocomotiveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocomotiveNotFoundException(Throwable cause) {
        super(cause);
    }

    public LocomotiveNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
