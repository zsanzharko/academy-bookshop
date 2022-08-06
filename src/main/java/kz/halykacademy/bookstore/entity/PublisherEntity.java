package kz.halykacademy.bookstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

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
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PublisherEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "title")
    private String title;

    @OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "publisher", targetEntity = BookEntity.class)
    @ToString.Exclude
    private Set<BookEntity> bookList;
}
