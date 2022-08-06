package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends BaseService<Book, BookEntity, BookRepository>
        implements BookService {

    @Autowired
    public BookServiceImpl(BookRepository repository, ModelMapper modelMapper) {
        super(BookEntity.class, Book.class, repository, modelMapper);
    }

    @Override
    public List<Book> findBookByName(String name) {
        return getModelMap(repository.findAllByTitle(name), Book.class);
    }

    @Override
    public Book create(Book book) {
        if (book.getPublisher() == null || book.getPublisher().getId() == null) return null;
        var bookEntity = getModelMap(book, entityClass);

        return save(bookEntity);
    }

    @Override
    public List<Book> create(List<Book> books) {
        for (var book : books)
            if (book.getPublisher() == null || book.getPublisher().getId() == null)
                return null;
        var bookEntities = getModelMap(books, entityClass);

        return saveAll(bookEntities);
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
        if (book.getPublisher() == null) return null;

        var bookEntity = getModelMap(book, entityClass);

        return saveAndFlush(bookEntity);
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
