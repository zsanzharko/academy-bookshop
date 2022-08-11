package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.DTOs;

import java.util.List;

public interface BaseCRUDOperation<T extends DTOs> {

    T create(T t) throws BusinessException;

    List<T> read() throws BusinessException;

    T read(Long id) throws BusinessException;

    T update(T t) throws BusinessException;

    void delete(Long id) throws BusinessException;
}
