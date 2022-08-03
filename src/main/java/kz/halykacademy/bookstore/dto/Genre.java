package kz.halykacademy.bookstore.dto;

import kz.halykacademy.bookstore.provider.providable.Providable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Serializable, Providable {
    private Long id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
