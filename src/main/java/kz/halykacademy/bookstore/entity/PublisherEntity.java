package kz.halykacademy.bookstore.entity;

import kz.halykacademy.bookstore.dto.Publisher;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote Entity getting from datasource.
 * Поля издателя: id, название, список изданных книг
 */
@Entity(name = "Publisher")
@Table(name = "publishers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PublisherEntity extends AbstractEntity implements Serializable {
    @Column(name = "title")
    private String title;
    @OneToMany(fetch = LAZY, cascade = {ALL}, mappedBy = "publisher", targetEntity = BookEntity.class,
            orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private List<BookEntity> books;

    @Builder
    public PublisherEntity(Long id, Date removed, String title, List<BookEntity> books) {
        super(id, removed);
        this.title = title;
        this.books = books;
    }

    public void addBook(BookEntity book) {
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(BookEntity book) {
        books.remove(book);
        book.setPublisher(null);
    }

    public Publisher convert() {
        return Publisher.builder()
                .id(super.getId())
                .title(title)
                .books(books.stream().map(BookEntity::getId).toList())
                .build();
    }
}
