package kz.halykacademy.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote Entity getting from datasource.
 * Поля издателя: id, название, список изданных книг
 */
@Entity
@Table(name = "publishers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PublisherEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "publisher", cascade = ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<BookEntity> bookList;

    public PublisherEntity(String title, Set<BookEntity> bookList) {
        this.title = title;
        this.bookList = bookList;
    }

    public PublisherEntity(String title) {
        this.title = title;
        this.bookList = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PublisherEntity that = (PublisherEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
