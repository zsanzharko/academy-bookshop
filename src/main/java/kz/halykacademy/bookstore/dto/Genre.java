package kz.halykacademy.bookstore.dto;

import kz.halykacademy.bookstore.serviceImpl.DTOs;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Genre implements Serializable, DTOs {
    private Long id;
    private String title;
    private List<Long> books;
    private List<Long> authors;

    @Builder
    public Genre(Long id, String title, List<Long> books, List<Long> authors) {
        this.id = id;
        this.title = title;
        this.books = books;
        this.authors = authors;
    }
}
