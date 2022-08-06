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
     * @param book Entity from database
     * @return DTOs DTO object
     * @apiNote Save book to database.
     */
    @Override
    protected Book save(@NonNull Book book) {
        return super.save(book);
    }

    /**
     * @param entities Entity from database
     * @return DTOs DTO object
     * @apiNote Save entities to database.
     */
    @Override
    protected List<Book> saveAll(@NonNull List<Book> entities) {
        List<Book> result = new ArrayList<>(entities.size());
        entities.forEach(entity -> result.add(save(entity)));
        return result;
    }

    @Override
    public List<Book> findBookByName(String name) {
        return getModelMap(repository.findAllByTitle(name), Book.class);
    }

    @Override
    public Book create(Book book) {
        if (book.getPublisher() == null || book.getPublisher().getId() == null) return null;
        return save(book);
    }

    @Override
    public List<Book> create(List<Book> books) {
        for (var book : books)
            if (book.getPublisher() == null || book.getPublisher().getId() == null)
                return null;
        return saveAll(books);
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
    public Book update(Book book) {
        return saveAndFlush(book);
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
