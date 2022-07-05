package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
}
