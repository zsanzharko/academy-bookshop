package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
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
    protected Book save(Book entity) {
        assert entity != null;
        if (entity.getPublisher() != null && entity.getPublisher().getId() == null)
            return null;
        else
            return super.save(entity);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    @Override
    protected List<Book> saveAll(List<Book> entities) {
        assert entities != null;

        for (var entity : entities)
            if (entity.getPublisher() != null && entity.getPublisher().getId() == null)
                return null;

        return super.saveAll(entities);
    }

    @Override
    public List<Book> findBookByName(String name) {
        var model = repository.findAllByTitle(name);
        return getModelMap(model, Book.class);
    }

    @Override
    public Book create(Book entity) {
        return save(entity);
    }

    @Override
    public List<Book> read() {
        return getAll();
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
}