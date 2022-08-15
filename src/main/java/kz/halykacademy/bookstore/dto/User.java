package kz.halykacademy.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.halykacademy.bookstore.enums.UserRule;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User implements DTOs {
    private Long id;
    private String username;
    @ToString.Include
    @JsonIgnore
    private String password;
    private UserRule rule;

    private List<Long> orders;

    @Builder
    public User(Long id, String username, UserRule rule, String password, List<Long> orders) {
        this.id = id;
        this.username = username;
        this.rule = rule;
        this.password = password;
        this.orders = orders == null ? new ArrayList<>() : orders;
    }
}
