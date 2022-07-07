package kz.halykacademy.bookstore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


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
public class PublisherEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "title")
    private String title;
    @OneToMany
    @ToString.Exclude
    private List<BookEntity> bookList;

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

//    @Override
//    public String toString() {
//        return "PublisherEntity{" +
//                "title='" + title + '\'' +
//                ", bookList=" + bookList.stream().map((b) -> b.getTitle() + " ") +
//                '}';
//    }
}
