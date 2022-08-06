package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.provider.providable.Providable;

import java.util.List;

public interface BaseCRUDOperation<T extends Providable> {

    T create(T entity);

    List<T> create(List<T> entities);

    List<T> read();

    T read(Long id);

    T update(T entity);

    void delete(Long id);

    void deleteAll();

    void deleteAll(List<Long> ids);
}
