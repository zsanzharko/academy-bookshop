package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.provider.providable.Providable;

import java.util.List;

public interface AuthorService extends CRUDService<Author>, Providable {

    List<Author> findAuthorByFIO(String name, String surname, String patronymic);
}
