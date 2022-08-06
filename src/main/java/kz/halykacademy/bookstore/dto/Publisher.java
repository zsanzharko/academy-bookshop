package kz.halykacademy.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import kz.halykacademy.bookstore.serviceImpl.DTOs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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
    private Set<Book> bookList;

    public Publisher(String title, Set<Book> bookList) {
        this.title = title;
        this.bookList = bookList;
    }

    public Publisher(String title) {
        this.title = title;
        this.bookList = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(getId(), publisher.getId()) && getTitle().equals(publisher.getTitle()) && Objects.equals(getBookList(), publisher.getBookList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }
}
