package kz.halykacademy.bookstore.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

@Entity(name = "Genres")
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GenreEntity extends AbstractEntity implements Serializable  {
    @Column(name = "genre_name")
    private String title;

    @OneToMany(fetch = LAZY, cascade = {MERGE, DETACH})
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private Set<BookEntity> books;
    @OneToMany(fetch = LAZY, cascade = {MERGE, DETACH})
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private Set<AuthorEntity> authors;
    @Builder
    public GenreEntity(Long id, Date removed, String title, Set<BookEntity> books, Set<AuthorEntity> authors) {
        super(id, removed);
        this.title = title;
        this.books = books;
        this.authors = authors;
    }
}
