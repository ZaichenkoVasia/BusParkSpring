package ua.mycompany.buspark.model.service.exception;

public class IncorrectValueRuntimeException extends RuntimeException {
    public IncorrectValueRuntimeException() {
    }

    public IncorrectValueRuntimeException(String message) {
        super(message);
    }

    public IncorrectValueRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
