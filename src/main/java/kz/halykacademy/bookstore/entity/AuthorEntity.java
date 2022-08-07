package kz.halykacademy.bookstore.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Sanzhar
 * @version 0.1
 * @apiNote DTO object for sending to rest.
 * Поля у автора: id, фамилия, имя, отчество, дата рождения, список написанных книг
 */
@Entity(name = "Author")
@Table(name = "authors")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorEntity extends AbstractEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "birthday")
    private Date birthday;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "publisher", targetEntity = BookEntity.class)
    @ToString.Exclude
    private Set<BookEntity> writtenBookList;

    @Builder
    public AuthorEntity(Long id, java.sql.Date removed, String name, String surname, String patronymic, Date birthday, Set<BookEntity> writtenBookList) {
        super(id, removed);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.writtenBookList = writtenBookList;
    }
}
