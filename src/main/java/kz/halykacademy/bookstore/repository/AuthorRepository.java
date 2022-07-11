package kz.halykacademy.bookstore.repository;

import kz.halykacademy.bookstore.entity.AuthorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CommonRepository<AuthorEntity> {

    List<AuthorEntity> findAllByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);
}
