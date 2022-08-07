package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PublisherServiceImpl extends BaseService<Publisher, PublisherEntity, PublisherRepository>
        implements PublisherService {

    public final BookRepository bookRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository repository, BookRepository bookRepository) {
        super(PublisherEntity.class, Publisher.class, repository);
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        var publisherList = repository.findAllByTitle(name);
        return publisherList.stream().map(PublisherEntity::convert).toList();
    }

    @Override
    public Publisher create(Publisher publisher) {
        PublisherEntity publisherEntity;
        try {
            publisherEntity = getEntity(publisher);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }
        return save(publisherEntity);
    }

    @Override
    public List<Publisher> create(List<Publisher> publishers) {
        List<PublisherEntity> publisherEntities;
        try {
            publisherEntities = publishers.stream().map(this::getEntity).toList();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }
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
        PublisherEntity publisherEntity;
        try {
            publisherEntity = getEntity(publisher);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }

        //todo think about updating universal algorithm.
        //  Need to check ids in book and add or remove this book in publisher
        //  Because Publisher is main entity
        if (publisherEntity.getBooks() != null)
            publisherEntity.getBooks().forEach(bookEntity -> {
                if (bookEntity == null || bookEntity.getPublisher() == null)
                    publisherEntity.addBook(bookEntity);
            });

        return saveAndFlush(publisherEntity);
    }

    @Override
    public void delete(Long id) {
        var publisherEntity = repository.findById(id).orElse(null);
        if (publisherEntity == null) return;

        if (publisherEntity.getBooks() != null)
            publisherEntity.getBooks().forEach(publisherEntity::removeBook);
        saveAndFlush(publisherEntity); // updating data in database to remove all books from publisher

        removeById(id);
    }

    @Override
    public void deleteAll() {
        List<PublisherEntity> publisherEntities;
        try {
            publisherEntities = repository.findAll();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            log.error("Can't get all publishers");
            return;
        }

        publisherEntities.forEach(publisherEntity -> {
            if (publisherEntity.getBooks() != null)
                publisherEntity.getBooks().forEach(bookEntity -> {
                    if (bookEntity == null || bookEntity.getPublisher() == null)
                        publisherEntity.removeBook(bookEntity);
                });
        });
        saveAllAndFlush(publisherEntities); // updating data in database to remove all books from publisher

        removeAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        var publisherEntities = repository.findAllById(ids);

        publisherEntities.forEach(publisherEntity -> {
            if (publisherEntity.getBooks() != null)
                publisherEntity.getBooks().forEach(bookEntity -> {
                    if (bookEntity == null || bookEntity.getPublisher() == null)
                        publisherEntity.removeBook(bookEntity);
                });
        });
        saveAllAndFlush(publisherEntities); // updating data in database to remove all books from publisher

        removeAll(ids);
    }

    private PublisherEntity getEntity(Publisher publisher) throws NullPointerException {
        if (publisher == null) throw new NullPointerException("Publisher can not be null");
        if (publisher.getBooks() == null) throw new NullPointerException("Books in publisher can not be null");

        List<BookEntity> bookEntities = bookRepository.findAllById(publisher.getBooks());

        return publisher.convert(bookEntities);
    }

    @Override
    protected Publisher convertToDto(PublisherEntity publisherEntity) {
        return publisherEntity.convert();
    }

    @Override
    protected PublisherEntity convertToEntity(Publisher publisher) {
        return getEntity(publisher);
    }
}
