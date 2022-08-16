package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        List<BookEntity> bookEntities = repository.findAllByTitle(name).stream()
                .filter(book -> book.getRemoved() == null).toList();

        return bookEntities.stream().map(this::convertToDto).toList();
    }

    @Override
    public Book create(Book book) throws BusinessException {
        var bookEntity = convertToEntity(book);
        if (bookEntity == null) return null;

        return save(bookEntity);
    }

    @Override
    public List<Book> read() throws BusinessException {
        return super.getAll();
    }

    @Override
    public Book read(Long id) throws BusinessException {
        return super.findById(id);
    }

    @Override
    public Book update(Book book) throws BusinessException {
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
    public void delete(Long id) throws BusinessException {
        removeById(id);
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
    protected BookEntity convertToEntity(Book book) throws BusinessException {
        if (book == null) throw new NullPointerException("Book can not be null");
        if (book.getPublisher() == null) throw new BusinessException("Publisher in books can not be null", HttpStatus.BAD_REQUEST);

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
