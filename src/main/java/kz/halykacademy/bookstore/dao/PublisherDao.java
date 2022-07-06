package kz.halykacademy.bookstore.dao;

import kz.halykacademy.bookstore.core.provider.BaseProvider;
import kz.halykacademy.bookstore.core.provider.Provider;
import kz.halykacademy.bookstore.core.provider.PublisherProvider;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import lombok.Getter;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherDao implements PublisherService, Provider<Publisher> {
    @Getter
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherDao(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher save(PublisherEntity entity) {
        entity = publisherRepository.save(entity);
        return getModelMap(entity, Publisher.class);
    }

    @Override
    public List<Publisher> saveAll(List<PublisherEntity> entities) {
        entities = IterableUtils.toList(publisherRepository.saveAll(entities));
        return getModelMap(entities, Publisher.class);
    }

    @Override
    public Publisher saveAndFlush(PublisherEntity entity) {
        entity = publisherRepository.save(entity);
        return getModelMap(entity, Publisher.class);
    }

    @Override
    public List<Publisher> saveAllAndFlush(List<PublisherEntity> entities) {
        entities = IterableUtils.toList(publisherRepository.saveAll(entities));
        return getModelMap(entities, Publisher.class);

    }

    @Override
    public void remove(PublisherEntity entity) {
        publisherRepository.delete(entity);
    }

    @Override
    public void removeAll(List<PublisherEntity> entities) {
        publisherRepository.deleteAll(entities);
    }

    @Override
    public void removeAll() {
        publisherRepository.deleteAll();
    }

    @Override
    public List<Publisher> getAll() {
        var publishers = getModelMap(IterableUtils.toList(publisherRepository.findAll()), Publisher.class);
        BaseProvider<Publisher> provider = new PublisherProvider(publishers);
        return provider.getAll();
    }
}
