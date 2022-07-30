package kz.halykacademy.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у книги: id, цена, список авторов, издатель, название, количество страниц, год выпуска
 */
@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany(mappedBy = "writtenBookList", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<AuthorEntity> authors;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    private PublisherEntity publisher;
    @Column(name = "title")
    private String title;
    @Column(name = "number_of_page")
    private Integer numberOfPage;
    @Column(name = "release_date")
    private Date releaseDate;

    public BookEntity(BigDecimal price, Set<AuthorEntity> authors, PublisherEntity publisher, String title, Integer numberOfPage, Date releaseDate) {
        this.price = price;
        this.authors = authors;
        this.publisher = publisher;
        this.title = title;
        this.numberOfPage = numberOfPage;
        this.releaseDate = releaseDate;
    }

    public BookEntity(BigDecimal price, PublisherEntity publisher, String title, Date releaseDate) {
        this.price = price;
        this.publisher = publisher;
        this.title = title;
        this.releaseDate = releaseDate;
        this.numberOfPage = 0;
        this.authors = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookEntity that = (BookEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
