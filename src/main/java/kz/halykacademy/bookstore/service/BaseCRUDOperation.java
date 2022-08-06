package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.serviceImpl.DTOs;

import java.util.List;

public interface BaseCRUDOperation<T extends DTOs> {

    T create(T t);

    List<T> create(List<T> ts);

    List<T> read();

    T read(Long id);

    T update(T t);

    void delete(Long id);

    void deleteAll();

    void deleteAll(List<Long> ids);
}
