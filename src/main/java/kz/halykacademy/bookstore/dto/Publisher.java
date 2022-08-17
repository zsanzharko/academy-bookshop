package kz.halykacademy.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля издателя: id, название, список изданных книг
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Publisher implements Serializable, DTOs {
    private Long id;
    private String title;
    private List<Long> books;

    public Publisher(String title, List<Long> books) {
        this.title = title;
        this.books = books;
    }

    public Publisher(String title) {
        this.title = title;
        this.books = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(getId(), publisher.getId()) && Objects.equals(getBooks(), publisher.getBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }
}
