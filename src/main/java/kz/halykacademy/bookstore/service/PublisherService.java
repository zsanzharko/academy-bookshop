package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Publisher;

import java.util.List;

public interface PublisherService extends BaseCRUDOperation<Publisher> {

    List<Publisher> findPublisherByName(String name);
}
