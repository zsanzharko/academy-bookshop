package kz.halykacademy.bookstore.entity;

import kz.halykacademy.bookstore.dto.Author;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

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

    @ManyToMany(fetch = LAZY, targetEntity = BookEntity.class)
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

    public Author convert() {
        return Author.builder()
                .id(super.getId())
                .name(name)
                .surname((surname))
                .patronymic(patronymic)
                .birthday(birthday)
                .writtenBooks(writtenBookList.stream().map(BookEntity::getId).collect(Collectors.toSet()))
                .build();
    }


}
