package kz.halykacademy.bookstore.controller.rest.advice;

import kz.halykacademy.bookstore.controller.rest.response.Response;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Response> businessHandleException(BusinessException e) {
        Response response = new Response(new Date(), e.getStatus(), e.getMessage());
        return new ResponseEntity<>(response, e.getStatus());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Response> handleException(NullPointerException e) {
        Response response = new Response(new Date(), HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
