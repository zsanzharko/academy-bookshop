package kz.halykacademy.bookstore.dao;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.provider.Provider;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import lombok.Getter;

import java.util.List;

public class PublisherDao implements PublisherService, Provider<Publisher> {
    @Getter
    private PublisherRepository publisherRepository;

    public PublisherDao(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher save(Publisher entity) {
        return null;
    }

    @Override
    public List<Publisher> saveAll(List<PublisherEntity> entities) {
        return null;
    }

    @Override
    public Publisher saveAndFlush(PublisherEntity entity) {
        return null;
    }

    @Override
    public List<Publisher> saveAllAndFlush(List<PublisherEntity> entities) {
        return null;
    }

    @Override
    public void remove(PublisherEntity entity) {

    }

    @Override
    public void removeAll(List<PublisherEntity> entities) {

    }

    @Override
    public List<Publisher> getItems() {
        return null;
    }

    @Override
    public List<Publisher> getAll() {
        return null;
    }
}
