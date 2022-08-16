package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.GenreRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.BookService;
import lombok.NonNull;
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
    private final GenreRepository genreRepository;

    @Autowired
    public BookServiceImpl(BookRepository repository,
                           PublisherRepository publisherRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        super(BookEntity.class, Book.class, repository);
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Book> findBookByName(String name) {
        List<BookEntity> bookEntities = repository.findAllByTitle(name).stream()
                .filter(book -> book.getRemoved() == null).toList();

        return bookEntities.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Book> findBooksByGenres(@NonNull List<String> genresName) throws BusinessException {
        if (genresName.size() == 0) throw new BusinessException("Invalid input on genres name", HttpStatus.BAD_REQUEST);

        var genresList = genresName.stream()
                .map(genreRepository::findAllByTitle)
                .toList();
        Set<Book> books = new HashSet<>();

        for (var genres : genresList) {
            for (var genre : genres) {
                if (genre.getRemoved() == null) {
                    var booksGenre = genre.getBooks().stream()
                            .map(this::convertToDto)
                            .collect(Collectors.toSet());
                    books.addAll(booksGenre);
                }
            }
        }

        return books.stream().toList();
    }

    @Override
    public Book create(Book book) throws BusinessException {
        var bookEntity = convertToEntity(book);

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

        return update(bookEntity);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        this.removeById(id);
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
        if (book.getPublisher() == null)
            throw new BusinessException("Publisher in books can not be null", HttpStatus.BAD_REQUEST);

        var publisher = publisherRepository.findById(book.getPublisher())
                .orElseThrow(() -> new BusinessException("Publsher can not found", HttpStatus.NOT_FOUND));

        Set<AuthorEntity> authorEntities = null;
        if (book.getAuthors() != null && !book.getAuthors().isEmpty())
            authorEntities = new HashSet<>(authorRepository.findAllById(book.getAuthors()));

        return BookEntity.builder()
                .id(book.getId())
                .price(book.getPrice())
                .title(book.getTitle())
                .authors(authorEntities)
                .numberOfPage(book.getNumberOfPage())
                .publisher(publisher)
                .releaseDate(book.getReleaseDate())
                .build();
    }

    @Override
    protected void removeById(Long id) throws BusinessException {
        var book = repository.findById(id).orElseThrow(() -> new BusinessException("Can not find book by id", HttpStatus.NOT_FOUND));
        var authors = authorRepository.findAllById(
                book.getAuthors().stream()
                .map(AuthorEntity::getId)
                .toList()
        );

        for (var author : authors) author.removeBook(book);
        authorRepository.saveAll(authors);

        super.removeById(id);
    }
}
