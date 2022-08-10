package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.GenreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenreRepository extends CommonRepository<GenreEntity>{
    List<GenreEntity> findAllByAuthorsIn(Set<AuthorEntity> authors);
}
