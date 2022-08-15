package kz.halykacademy.bookstore.controller.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Response {
    private Date date;
    private HttpStatus status;
    private String message;
}
