package kz.halykacademy.bookstore.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class Genre implements Serializable, DTOs {
    private Long id;
    private String title;
    private Set<Long> books;
    private Set<Long> authors;

    @Builder
    public Genre(Long id, String title, Set<Long> books, Set<Long> authors) {
        this.id = id;
        this.title = title;
        this.books = books;
        this.authors = authors;
    }
}
