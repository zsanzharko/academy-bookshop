package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl extends BaseService<Book, BookEntity, BookRepository>
        implements BookService {

    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public BookServiceImpl(BookRepository repository,
                           PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        super(BookEntity.class, Book.class, repository);
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findBookByName(String name) {
        List<BookEntity> bookEntities = repository.findAllByTitle(name);

        return bookEntities.stream().map(this::convertToDto).toList();
    }

    @Override
    public Book create(Book book) {
        var bookEntity = convertToEntity(book);
        if (bookEntity == null) return null;

        return save(bookEntity);
    }

    @Override
    public List<Book> create(@NonNull List<Book> books) {
        List<BookEntity> bookEntities = new ArrayList<>(books.size());
        for (Book book : books) {
            var bookEntity = convertToEntity(book);
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
        return super.findById(id);
    }

    @Override
    public Book update(Book book) {
        var bookEntity = convertToEntity(book);
        if (bookEntity == null) return null;

        if (bookEntity.getAuthors() != null) {
            bookEntity.getAuthors().forEach(author -> {
//                if (author == null || author.geta
            });
        }
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

    @Override
    protected Book convertToDto(BookEntity bookEntity) {
        return Book.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .authors((bookEntity.getAuthors() == null) ? null :
                        bookEntity.getAuthors().stream().map(AuthorEntity::getId).collect(Collectors.toSet()))
                .numberOfPage(bookEntity.getNumberOfPage())
                .publisher(bookEntity.getPublisher() == null ? null : bookEntity.getPublisher().getId())
                .price(bookEntity.getPrice())
                .releaseDate(bookEntity.getReleaseDate()).build();
    }

    @Override
    protected BookEntity convertToEntity(Book book) {
        if (book.getPublisher() == null) return null;

        PublisherEntity publisherEntity = publisherRepository.findById(book.getPublisher()).orElse(null);
        if(publisherEntity == null) return null;

        Set<AuthorEntity> authorEntities = null;
        if (book.getAuthors() != null && !book.getAuthors().isEmpty())
            authorEntities = new HashSet<>(authorRepository.findAllById(book.getAuthors()));
        return BookEntity.builder()
                        .id(book.getId())
                        .price(book.getPrice())
                        .title(book.getTitle())
                        .authors(authorEntities)
                        .numberOfPage(book.getNumberOfPage())
                        .publisher(publisherEntity)
                        .releaseDate(book.getReleaseDate())
                        .build();
    }
}
