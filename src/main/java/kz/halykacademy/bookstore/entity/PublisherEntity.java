package kz.halykacademy.bookstore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote Entity getting from datasource.
 * Поля издателя: id, название, список изданных книг
 */
@Entity
@Table(name = "publishers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PublisherEntity implements Serializable, Entitiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "title")
    private String title;
    @OneToMany
    @ToString.Exclude
    private Set<BookEntity> bookList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PublisherEntity that = (PublisherEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
