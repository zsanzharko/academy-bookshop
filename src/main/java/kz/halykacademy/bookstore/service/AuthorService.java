package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.repository.RepositoryHandler;

public interface AuthorService extends RepositoryHandler<AuthorEntity, Author> {
}
