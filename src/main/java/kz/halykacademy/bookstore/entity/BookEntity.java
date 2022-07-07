package kz.halykacademy.bookstore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany
    @JoinTable(
            name = "written_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private Set<AuthorEntity> authors;
    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    private PublisherEntity publisher;
    @Column(name = "title")
    private String title;
    @Column(name = "number_of_page")
    private Integer numberOfPage;
    @Column(name = "release_date")
    private Date releaseDate;

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

