package kz.halykacademy.bookstore.entity;

import kz.halykacademy.bookstore.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity(name = "Order")
@Table(name = "orders")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderEntity extends AbstractEntity {
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
    @OneToMany(fetch = FetchType.EAGER, cascade = {MERGE, DETACH, REFRESH}, targetEntity = BookEntity.class)
    @JoinTable(name = "order_books")
    @ToString.Exclude
    private Set<BookEntity> bookEntityList;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "order_time")
    private Date time;

    @Builder
    public OrderEntity(Long id, java.sql.Date removed, UserEntity user, OrderStatus status, Set<BookEntity> bookEntityList, Date time) {
        super(id, removed);
        this.user = user;
        this.status = status;
        this.bookEntityList = bookEntityList;
        this.time = time;
    }
}
