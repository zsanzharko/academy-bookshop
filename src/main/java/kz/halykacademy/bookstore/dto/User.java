package kz.halykacademy.bookstore.dto;

import kz.halykacademy.bookstore.enums.UserRule;
import kz.halykacademy.bookstore.serviceImpl.DTOs;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements DTOs {
    private Long id;
    private String username;
    @ToString.Include
    private String password;
    private UserRule rule;

    @Builder
    public User(Long id, String username, UserRule rule, String password) {
        this.id = id;
        this.username = username;
        this.rule = rule;
        this.password = password;
    }
}
