package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<UserEntity>{

    UserEntity findByUsername(String username);
}
