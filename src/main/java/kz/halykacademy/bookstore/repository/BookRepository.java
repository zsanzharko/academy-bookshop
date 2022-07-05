package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
}
