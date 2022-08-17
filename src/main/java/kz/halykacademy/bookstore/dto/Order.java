package kz.halykacademy.bookstore.dto;

import kz.halykacademy.bookstore.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class Order implements DTOs {

    private Long id;

    private Long user;
    private OrderStatus status;
    private Date time;
    private Set<Long> books;

    @Builder
    public Order(Long id, Long user, OrderStatus status, Date time, Set<Long> books) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.time = time;
        this.books = books;
    }
}
