package kz.halykacademy.bookstore.entity;

import kz.halykacademy.bookstore.dto.Book;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у книги: id, цена, список авторов, издатель, название, количество страниц, год выпуска
 */
@Entity(name = "Book")
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity extends AbstractEntity implements Serializable {
    @NaturalId
    @Column(name = "title")
    private String title;
    @Column(name = "number_of_page")
    private Integer numberOfPage;
    @Column(name = "release_date")
    private Date releaseDate;
    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(mappedBy = "writtenBookList", cascade = {ALL}, targetEntity = AuthorEntity.class)
    @ToString.Exclude
    private Set<AuthorEntity> authors;

    @ManyToOne(fetch = LAZY, targetEntity = PublisherEntity.class)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    @ToString.Exclude
    private PublisherEntity publisher;

    @Builder
    public BookEntity(Long id, java.sql.Date removed, String title, Integer numberOfPage, Date releaseDate, BigDecimal price, Set<AuthorEntity> authors, PublisherEntity publisher) {
        super(id, removed);
        this.title = title;
        this.numberOfPage = numberOfPage;
        this.releaseDate = releaseDate;
        this.price = price;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book convert(){
        return Book.builder()
                .id(super.getId())
                .title(title)
                .authors(authors.stream().map(AuthorEntity::getId).collect(Collectors.toSet()))
                .numberOfPage(numberOfPage)
                .publisher(publisher.getId())
                .price(price)
                .releaseDate(releaseDate)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BookEntity book = (BookEntity) o;
        return Objects.equals(title, book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

