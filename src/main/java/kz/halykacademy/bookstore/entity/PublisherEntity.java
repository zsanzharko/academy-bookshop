package kz.halykacademy.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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
public class PublisherEntity implements Serializable, Entitiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "title")
    private String title;
    @OneToMany
    private Set<BookEntity> bookList;
}
