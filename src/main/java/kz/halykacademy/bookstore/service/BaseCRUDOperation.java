package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.serviceImpl.DTOs;

import java.util.List;

public interface BaseCRUDOperation<T extends DTOs> {

    T create(T entity);

    List<T> create(List<T> entities);

    List<T> read();

    T read(Long id);

    T update(T entity);

    void delete(Long id);

    void deleteAll();

    void deleteAll(List<Long> ids);
}
