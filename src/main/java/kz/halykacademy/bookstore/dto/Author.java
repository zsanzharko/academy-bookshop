package kz.halykacademy.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import kz.halykacademy.bookstore.serviceImpl.DTOs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у автора: id, фамилия, имя, отчество, дата рождения, список написанных книг
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Author implements Serializable, DTOs {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthday;
    private Set<Book> writtenBooks;

    public Author(String name, String surname, String patronymic, Date birthday, Set<Book> writtenBooks) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.writtenBooks = writtenBooks;
    }

    public Author(String name, String surname, String patronymic, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.writtenBooks = new HashSet<>();
    }

    public Author(String name, String surname, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.patronymic = "";
        this.birthday = birthday;
        this.writtenBooks = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return getId().equals(author.getId()) &&
                getName().equals(author.getName()) &&
                (getWrittenBooks() != null && getWrittenBooks().equals(author.getWrittenBooks()))
                ||
                getId().equals(author.getId()) &&
                        getName().equals(author.getName()) &&
                        (getWrittenBooks() == null || getWrittenBooks().equals(author.getWrittenBooks()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBirthday(), getWrittenBooks());
    }
}
