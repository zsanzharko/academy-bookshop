package kz.halykacademy.bookstore.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля издателя: id, название, список изданных книг
 */
@Data
public class Publisher implements Serializable {
    private Long id;
    private String title;
    private List<Book> bookList;
}
