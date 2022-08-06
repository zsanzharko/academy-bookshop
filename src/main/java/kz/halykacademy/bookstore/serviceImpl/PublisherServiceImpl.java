package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl extends BaseService<Publisher, PublisherEntity, PublisherRepository>
        implements PublisherService {

    public PublisherServiceImpl(PublisherRepository repository, ModelMapper modelMapper) {
        super(PublisherEntity.class, Publisher.class, repository, modelMapper);
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        var publisherList = repository.findAllByTitle(name);
        return getModelMap(publisherList, Publisher.class);
    }

    @Override
    public Publisher create(Publisher publisher) {
        var publisherEntity = getModelMap(publisher, entityClass);

        if (publisherEntity.getBooks() != null)
            publisherEntity.getBooks().forEach(bookEntity -> {
                if (bookEntity == null || bookEntity.getPublisher() == null)
                    publisherEntity.addBook(bookEntity);
            });

        return save(publisherEntity);
    }

    @Override
    public List<Publisher> create(List<Publisher> publishers) {
        var publisherEntities = getModelMap(publishers, entityClass);


        publisherEntities.forEach(publisherEntity -> {
            if (publisherEntity.getBooks() != null)
                publisherEntity.getBooks().forEach(bookEntity -> {
                    if (bookEntity == null || bookEntity.getPublisher() == null)
                        publisherEntity.addBook(bookEntity);
                });
        });

        return saveAll(publisherEntities);
    }

    @Override
    public List<Publisher> read() {
        return super.getAll();
    }

    @Override
    public Publisher read(Long id) {
        return super.findById(id);
    }

    @Override
    public Publisher update(Publisher publisher) {
        var publisherEntity = getModelMap(publisher, entityClass);

        if (publisherEntity.getBooks() != null)
            publisherEntity.getBooks().forEach(bookEntity -> {
                if (bookEntity == null || bookEntity.getPublisher() == null)
                    publisherEntity.addBook(bookEntity);
            });

        return saveAndFlush(publisherEntity);
    }

    @Override
    public void delete(Long id) {
        var publisherEntity = getModelMap(findById(id), entityClass);

        if (publisherEntity.getBooks() != null)
            publisherEntity.getBooks().forEach(bookEntity -> {
                if (bookEntity == null || bookEntity.getPublisher() == null)
                    publisherEntity.removeBook(bookEntity);
            });
        saveAndFlush(publisherEntity);

        removeById(id);
    }

    @Override
    public void deleteAll() {
        var publisherEntities = getModelMap(getAll(), entityClass);


        publisherEntities.forEach(publisherEntity -> {
            if (publisherEntity.getBooks() != null)
                publisherEntity.getBooks().forEach(bookEntity -> {
                    if (bookEntity == null || bookEntity.getPublisher() == null)
                        publisherEntity.removeBook(bookEntity);
                });
        });
        saveAllAndFlush(publisherEntities);

        removeAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        var publisherEntities = getModelMap(repository.findAllById(ids), entityClass);

        publisherEntities.forEach(publisherEntity -> {
            if (publisherEntity.getBooks() != null)
                publisherEntity.getBooks().forEach(bookEntity -> {
                    if (bookEntity == null || bookEntity.getPublisher() == null)
                        publisherEntity.removeBook(bookEntity);
                });
        });

        saveAllAndFlush(publisherEntities);

        removeAll(ids);
    }


}
