package kz.halykacademy.bookstore.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import static javax.persistence.CascadeType.*;

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
    @ManyToMany(cascade = {MERGE, DETACH, REFRESH}, fetch = FetchType.LAZY, targetEntity = BookEntity.class)
    @JoinTable(name = "book_genres",
            joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<BookEntity> books;

    @ManyToMany(cascade = {CascadeType.MERGE, DETACH, REFRESH}, fetch = FetchType.LAZY, targetEntity = AuthorEntity.class)
    @JoinTable(name = "author_genres",
            joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
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
