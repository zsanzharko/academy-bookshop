package kz.halykacademy.bookstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GenreEntity extends AbstractEntity implements Serializable, Entitiable  {

    @Column(name = "genre_name")
    private String name = "null";

    @ManyToOne
    private BookEntity book;
}
