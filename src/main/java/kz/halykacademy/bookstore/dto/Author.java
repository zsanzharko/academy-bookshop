package kz.halykacademy.bookstore.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у автора: id, фамилия, имя, отчество, дата рождения, список написанных книг
 */
@Data
public class Author implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthday;
    private List<Book> readed;
}
