package kz.halykacademy.bookstore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorEntity extends AbstractEntity implements Serializable, Entitiable {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "birthday")
    private Date birthday;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "written_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<BookEntity> writtenBookList;

    public AuthorEntity(String name, String surname, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.patronymic = "";
        this.birthday = birthday;
        this.writtenBookList = new HashSet<>();
    }

    public AuthorEntity(String name, String surname, String patronymic, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.writtenBookList = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
