package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl extends BaseService<Book, BookEntity, BookRepository>
        implements BookService {

    public BookServiceImpl(BookRepository repository) {
        super(BookEntity.class, Book.class, repository);
    }

    /**
     * @param entity Entity from database
     * @return DTOs DTO object
     * @apiNote Save entity to database.
     */
    @Override
    protected Book save(@NonNull Book entity) {
        return super.save(entity);
    }

    /**
     * @param entities Entity from database
     * @return DTOs DTO object
     * @apiNote Save entities to database.
     */
    @Override
    protected List<Book> saveAll(@NonNull List<Book> entities) {
        List<Book> result = new ArrayList<>(entities.size());
        for (var entity : entities) {
            var res = save(entity);
            result.add(res);
        }
        return result;
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
    public List<Book> create(List<Book> entities) {
        return saveAll(entities);
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