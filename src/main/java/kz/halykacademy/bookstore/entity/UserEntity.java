package kz.halykacademy.bookstore.entity;

import kz.halykacademy.bookstore.enums.UserRule;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, targetEntity = OrderEntity.class)
    @ToString.Exclude
    private List<OrderEntity> orders;

    @Builder
    public UserEntity(Long id, Date removed, String username, String password, UserRule rule, List<OrderEntity> orders) {
        super(id, removed);
        this.username = username;
        this.password = password;
        this.rule = rule;
        this.orders = orders;
    }
}
