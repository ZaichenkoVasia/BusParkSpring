package ua.mycompany.buspark.model.service.exception;

public class InvalidPaginatingRuntimeException extends RuntimeException {
    public InvalidPaginatingRuntimeException() {
    }

    public InvalidPaginatingRuntimeException(String message) {
        super(message);
    }

    public InvalidPaginatingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
