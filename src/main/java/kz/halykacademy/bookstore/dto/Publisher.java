package kz.halykacademy.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import kz.halykacademy.bookstore.provider.providable.ShopProvidable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля издателя: id, название, список изданных книг
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Publisher implements Serializable, ShopProvidable {
    private Long id;
    private String title;
    private Set<Book> bookList;

    public Publisher(String title, Set<Book> bookList) {
        this.title = title;
        this.bookList = bookList;
    }

    public Publisher(String title) {
        this.title = title;
        this.bookList = null;
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
