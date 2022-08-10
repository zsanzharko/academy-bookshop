package kz.halykacademy.bookstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

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

    @OneToMany
    @ToString.Exclude
    private List<BookEntity> books;
    @OneToMany
    @ToString.Exclude
    private List<AuthorEntity> authors;
    @Builder
    public GenreEntity(Long id, Date removed, String title, List<BookEntity> books, List<AuthorEntity> authors) {
        super(id, removed);
        this.title = title;
        this.books = books;
        this.authors = authors;
    }
}
