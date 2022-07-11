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
    public Book save(Book entity) {
        assert entity != null;
        if (entity.getPublisher() != null && entity.getPublisher().getId() == null) return null;
        return super.save(entity);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    @Override
    public List<Book> saveAll(List<Book> entities) {
        assert entities != null;

        for (var entity : entities)
            if (entity.getPublisher() != null && entity.getPublisher().getId() == null)
                return null;

        return super.saveAll(entities);
    }
}
