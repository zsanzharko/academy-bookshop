package kz.halykacademy.bookstore.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.*;
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
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "title")
    private String title;
    @Column(name = "number_of_page")
    private Integer numberOfPage;
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "price")
    private BigDecimal price;


    @ManyToMany(mappedBy = "writtenBookList", cascade = {
            MERGE,
            REFRESH,
            REMOVE,
            DETACH
    }, targetEntity = AuthorEntity.class)
    @ToString.Exclude
    private Set<AuthorEntity> authors;

    @ManyToOne(fetch = LAZY, cascade = {
            MERGE,
            REFRESH,
            REMOVE,
            DETACH,
            PERSIST}, targetEntity = PublisherEntity.class)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    @ToString.Exclude
    private PublisherEntity publisher;
}

