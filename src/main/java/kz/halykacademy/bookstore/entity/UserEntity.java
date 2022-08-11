package kz.halykacademy.bookstore.entity;

import kz.halykacademy.bookstore.enums.UserRule;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "User")
@Table(name = "user")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserEntity extends AbstractEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "rule", length = 15)
    @Enumerated(EnumType.STRING)
    private UserRule rule;

    @Builder
    public UserEntity(Long id, Date removed, String username, String password, UserRule rule) {
        super(id, removed);
        this.username = username;
        this.password = password;
        this.rule = rule;
    }
}
