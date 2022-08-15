package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.BookstoreApplication;
import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.serviceImpl.AuthorServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.GenreServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BookstoreApplication.class)
@Slf4j
public class GenreServiceImplTest {
    @Autowired
    private GenreServiceImpl service;
    @Autowired
    private PublisherServiceImpl publisherService;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private AuthorServiceImpl authorService;

    @BeforeEach
    void cleanBefore() {
        var genres= service.read();
        genres.forEach(genre -> {
            try {
                service.delete(genre.getId());
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    @DisplayName("Test service")
    void setUp() {
        assertNotNull(service, "Service did not autowired in test");
    }


    @Test
    @DisplayName("Save Genre")
    void saveGenre() throws BusinessException {
        var genre = new Genre(null, "Dramatic", null, null);

        val dbGenre = service.create(genre);

        Assertions.assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());
    }

    @Test
    @DisplayName("Save Genre with Books")
    void saveGenreWithBooks() throws BusinessException {
        var genre = new Genre(null, "Comedy", Set.of(createBook().getId()), new HashSet<>());

        val dbGenre = service.create(genre);

        Assertions.assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());
    }

    @Test
    @DisplayName("Save Genre with Books and Author")
    void saveGenreWithBooksAndAuthor() throws BusinessException {
        val book = createBookWithAuthor();
        var genre = new Genre(null, "Comedy", Set.of(book.getId()), new HashSet<>(book.getAuthors()));

        val dbGenre = service.create(genre);

        Assertions.assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());
        assertEquals(genre.getBooks().size(), dbGenre.getBooks().size());
        assertEquals(genre.getAuthors().size(), dbGenre.getAuthors().size());
    }

    @Test
    @DisplayName("Update Genre")
    void updateGenre() throws BusinessException {
        var genre = new Genre(null, "Dramatic", null, null);

        val dbGenre = service.create(genre);

        dbGenre.setTitle("Horror");

        val updateGenre = service.update(dbGenre);

        assertNotNull(updateGenre);
        assertNotEquals(genre.getTitle(), updateGenre.getTitle());
        assertEquals("Horror", updateGenre.getTitle());
    }

    @Test
    @DisplayName("Update genre with books")
    void updateGenreWithBooks() throws BusinessException {
        val book = createBookWithAuthor();
        var genre = new Genre(null, "Comedy", Set.of(book.getId()), new HashSet<>(book.getAuthors()));

        val dbGenre = service.create(genre);

        dbGenre.setTitle("Horror");
        dbGenre.setBooks(null);

        val updateGenre = service.update(dbGenre);

        assertNotNull(updateGenre);
        assertNotEquals(genre.getTitle(), updateGenre.getTitle());
        assertEquals("Horror", updateGenre.getTitle());
        assertEquals(0, updateGenre.getBooks().size());
        assertEquals(0, updateGenre.getAuthors().size());
    }

    @Test
    @DisplayName("Delete genre by id")
    void deleteGenreById() throws BusinessException {
        var genre = new Genre(null, "Dramatic", null, null);

        val dbGenre = service.create(genre);

        assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());

        service.delete(dbGenre.getId());

        assertFalse(service.getRepository().findById(dbGenre.getId()).isEmpty());
    }

    @Test
    @DisplayName("Delete genre with book by id")
    void deleteGenreWithBookById() throws BusinessException {
        val book = createBookWithAuthor();
        var genre = new Genre(null, "Comedy", Set.of(book.getId()), new HashSet<>(book.getAuthors()));

        val dbGenre = service.create(genre);

        assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());

        service.delete(dbGenre.getId());

        assertFalse(service.getRepository().findById(dbGenre.getId()).isEmpty());
    }

    @Test
    @DisplayName("Delete genres by id")
    void deleteGenresById() throws BusinessException {
        val book = createBookWithAuthor();
        var genres = List.of(
                new Genre(null, "Comedy", Set.of(book.getId()), new HashSet<>(book.getAuthors())),
                new Genre(null, "Indie", Set.of(book.getId()),new HashSet<>(book.getAuthors())),
                new Genre(null, "Fighting", Set.of(book.getId()), new HashSet<>(book.getAuthors()))
        );


        val dbGenre = genres.stream().map(genre -> {
            try {
                return service.create(genre);
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        service.read().forEach(genre -> {
            try {
                service.delete(genre.getId());
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });

        assertFalse(service.getRepository().findAllById(dbGenre.stream()
                .map(Genre::getId).toList())
                .isEmpty());
    }

    private Book createBook() throws BusinessException {
        val publisher = publisherService.create(new Publisher("Marvel"));
        return bookService.create(new Book(BigDecimal.ZERO, publisher.getId(), "Earth", new Date()));
    }

    private Book createBookWithAuthor() throws BusinessException {
        val publisher = publisherService.create(new Publisher("Marvel"));
        val author = authorService.create(new Author("Sanzhar", "Zhanibekov", new Date()));
        val book = new Book(BigDecimal.ZERO, publisher.getId(), "Earth", new Date());
        book.setAuthors(Set.of(author.getId()));
        book.setPublisher(publisher.getId());
        return  bookService.create(book);
    }

    @AfterAll
    static void cleanup() throws BusinessException {
        var service = ApplicationContextProvider.getApplicationContext()
                .getBean(GenreServiceImpl.class);
        var publisherService = ApplicationContextProvider.getApplicationContext()
                .getBean(PublisherServiceImpl.class);
        var bookService = ApplicationContextProvider.getApplicationContext()
                .getBean(BookServiceImpl.class);
        var authorService = ApplicationContextProvider.getApplicationContext()
                .getBean(AuthorServiceImpl.class);

        service.read().forEach(genre -> {
            try {
                service.delete(genre.getId());
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
        bookService.read().forEach(book -> {
            try {
                bookService.delete(book.getId());
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
        authorService.read().forEach(author -> {
            try {
                authorService.delete(author.getId());
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
        publisherService.read().forEach(publisher -> {
            try {
                publisherService.delete(publisher.getId());
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
