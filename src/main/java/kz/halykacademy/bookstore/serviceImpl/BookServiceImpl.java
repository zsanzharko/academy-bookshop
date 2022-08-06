package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl extends BaseService<Book, BookEntity, BookRepository>
        implements BookService {

    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public BookServiceImpl(BookRepository repository, ModelMapper modelMapper,
                           PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        super(BookEntity.class, Book.class, repository, modelMapper);
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findBookByName(String name) {
        List<BookEntity> bookEntities = repository.findAllByTitle(name);

        return bookEntities.stream().map(BookEntity::convert).toList();
    }

    @Override
    public Book create(Book book) {
        var bookEntity = getEntity(book);
        if (bookEntity == null) return null;
        return save(bookEntity);
    }

    @Override
    public List<Book> create(@NonNull List<Book> books) {
        List<BookEntity> bookEntities = new ArrayList<>(books.size());
        for (Book book : books) {
            var bookEntity = getEntity(book);
            if (bookEntity == null) return null;
            bookEntities.add(bookEntity);
        }
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
        var bookEntity = getEntity(book);
        if (bookEntity == null) return null;
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

    private BookEntity getEntity(Book book) {
        if (book.getPublisher() == null) return null;

        PublisherEntity publisherEntity = publisherRepository.findById(book.getPublisher()).orElse(null);
        if(publisherEntity == null) return null;

        Set<AuthorEntity> authorEntities = null;
        if (book.getAuthors() != null || !book.getAuthors().isEmpty())
            authorEntities = new HashSet<>(authorRepository.findAllById(book.getAuthors()));
        return  book.convert(authorEntities, publisherEntity);
    }

    @Override
    protected Book convertToDto(BookEntity bookEntity) {
        return bookEntity.convert();
    }

    @Override
    protected BookEntity convertToEntity(Book book) {
        return getEntity(book);
    }
}
