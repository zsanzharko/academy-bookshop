package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Author;

import java.util.List;

public interface AuthorService extends CRUDService<Author>{

    List<Author> findAuthorByFIO(String name, String surname, String patronymic);
}
