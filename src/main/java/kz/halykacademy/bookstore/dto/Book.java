package kz.halykacademy.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import kz.halykacademy.bookstore.serviceImpl.DTOs;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у книги: id, цена, список авторов, издатель, название, количество страниц, год выпуска
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Book implements Serializable, DTOs {
    private Long id;
    private BigDecimal price;
    private Set<Long> authors;
    private Long publisher;
    private String title;
    private Integer numberOfPage;
    private Date releaseDate;

    public Book(BigDecimal price, @NonNull Set<Long> authors, @NonNull Long publisher, String title, Integer numberOfPage, Date releaseDate) {
        this.price = price;
        this.authors = authors;
        this.publisher = publisher;
        this.title = title;
        this.numberOfPage = numberOfPage;
        this.releaseDate = releaseDate;
    }

    public Book(BigDecimal price, Long publisher, String title, Date releaseDate) {
        this.price = price;
        this.publisher = publisher;
        this.title = title;
        this.releaseDate = releaseDate;
        this.numberOfPage = 0;
        this.authors = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getPrice(), book.getPrice()) && getTitle().equals(book.getTitle()) && Objects.equals(getNumberOfPage(), book.getNumberOfPage()) && getReleaseDate().equals(book.getReleaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getTitle(), getNumberOfPage(), getReleaseDate());
    }
}

