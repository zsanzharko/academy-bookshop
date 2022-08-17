package kz.halykacademy.bookstore.exceptions.businessExceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserInvalidException extends BusinessException {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UserInvalidException(String message, HttpStatus status) {
        super(message, status);
    }
}

