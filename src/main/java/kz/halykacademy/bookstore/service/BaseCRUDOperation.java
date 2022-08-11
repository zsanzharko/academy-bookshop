package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.serviceImpl.DTOs;

import java.util.List;

public interface BaseCRUDOperation<T extends DTOs> {

    T create(T t);

    List<T> read();

    T read(Long id);

    T update(T t);

    void delete(Long id);
}
