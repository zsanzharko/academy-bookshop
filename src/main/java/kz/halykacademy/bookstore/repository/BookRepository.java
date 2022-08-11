package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CommonRepository<BookEntity> {

    List<BookEntity> findAllByTitle(String title);
}
