package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Publisher;

import java.util.List;

public interface PublisherService extends CRUDService<Publisher> {

    List<Publisher> findPublisherByName(String name);
}
