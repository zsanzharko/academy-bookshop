package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;

import java.util.List;

public interface AuthorService extends BaseLogicManager<Author> {

    List<Author> findAuthorByFIO(String name, String surname, String patronymic);

    List<Author> findAuthorsByGenres(List<String> genresName) throws BusinessException;
}
