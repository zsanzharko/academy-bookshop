package kz.halykacademy.bookstore.dto;

import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.serviceImpl.DTOs;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements DTOs {
    private Long id;
    private String username;
    @ToString.Include
    private String password;
    private UserRule rule;

    private List<Long> orders;

    @Builder
    public User(Long id, String username, UserRule rule, String password, List<Long> orders) {
        this.id = id;
        this.username = username;
        this.rule = rule;
        this.password = password;
        this.orders = orders == null ? new ArrayList<>(): orders;
    }
}