package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookProvider extends BaseProvider<Book, BookEntity, BookRepository> implements BookService {

    public BookProvider(BookRepository repository) {
        super(BookEntity.class, Book.class, repository);
    }

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Save entity to database.
     */
    @Override
    protected Book save(@NonNull Book entity) {
        return (entity.getPublisher() != null && entity.getPublisher().getId() == null) ?
                null : super.save(entity);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    @Override
    protected List<Book> saveAll(@NonNull List<Book> entities) {
        for (var entity : entities)
            if (entity.getPublisher() != null && entity.getPublisher().getId() == null)
                return null;

        return super.saveAll(entities);
    }

    @Override
    public List<Book> findBookByName(String name) {
        return getModelMap(repository.findAllByTitle(name), Book.class);
    }

    @Override
    public Book create(Book entity) {
        return save(entity);
    }

    @Override
    public List<Book> read() {
        return super.getAll();
    }

    @Override
    public Book read(Long id) {
        return findById(id);
    }

    @Override
    public Book update(Book entity) {
        return saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public void deleteAll() {
        removeAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        removeAll(ids);
    }
}
