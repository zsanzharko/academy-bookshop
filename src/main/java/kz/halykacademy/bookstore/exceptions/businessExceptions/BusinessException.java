package kz.halykacademy.bookstore.exceptions.businessExceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {

    @Getter
    private final HttpStatus status;
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public BusinessException(HttpStatus status) {
        this.status = status;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
