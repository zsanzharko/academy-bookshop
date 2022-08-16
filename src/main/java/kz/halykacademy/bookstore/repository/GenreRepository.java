package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.GenreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends CommonRepository<GenreEntity> {
    List<GenreEntity> findAllByTitle(String title);

}
