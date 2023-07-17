package manager;

public class ManagerValidateException extends RuntimeException {
    public ManagerValidateException() {
    }

    public ManagerValidateException(String message) {
        super(message);
    }

    public ManagerValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
