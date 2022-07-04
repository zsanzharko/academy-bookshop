package kz.halykacademy.bookstore.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у книги: id, цена, список авторов, издатель, название, количество страниц, год выпуска
 */
@Data
public class Book implements Serializable {
    private Long id;
    private BigDecimal price;
    private List<Author> authors;
    private Publisher publisher;
    private String title;
    private Integer numberOfPage;
    private Date releaseDate;
}

