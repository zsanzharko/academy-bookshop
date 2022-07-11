package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.provider.providable.Providable;

import java.util.List;

public interface CRUDService<T extends Providable> {

    T create(T entity);

    List<T> read();

    T read(Long id);

    T update(T entity);

    void delete(T entity);
}
