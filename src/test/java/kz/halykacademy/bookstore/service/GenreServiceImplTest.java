package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.BookstoreApplication;
import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.dto.Publisher;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        service.read().forEach(genre -> service.delete(genre.getId()));
        bookService.read().forEach(book -> bookService.delete(book.getId()));
        authorService.read().forEach(author -> authorService.delete(author.getId()));
        publisherService.read().forEach(publisher -> publisherService.delete(publisher.getId()));
    }

    @Test
    @DisplayName("Test service")
    void setUp() {
        assertNotNull(service, "Service did not autowired in test");
    }


    @Test
    @DisplayName("Save Genre")
    void saveGenre() {
        var genre = new Genre(null, "Dramatic", null, null);

        val dbGenre = service.create(genre);

        Assertions.assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());
    }

    @Test
    @DisplayName("Save Genre with Books")
    void saveGenreWithBooks() {
        var genre = new Genre(null, "Comedy", List.of(createBook().getId()), null);

        val dbGenre = service.create(genre);

        Assertions.assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());
    }

    @Test
    @DisplayName("Save Genre with Books and Author")
    void saveGenreWithBooksAndAuthor() {
        val book = createBookWithAuthor();
        var genre = new Genre(null, "Comedy", List.of(book.getId()), new ArrayList<>(book.getAuthors()));

        val dbGenre = service.create(genre);

        Assertions.assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());
        assertEquals(genre.getBooks().size(), dbGenre.getBooks().size());
        assertEquals(genre.getAuthors().size(), dbGenre.getAuthors().size());
    }

    @Test
    @DisplayName("Update Genre")
    void updateGenre() {
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
    void updateGenreWithBooks() {
        val book = createBookWithAuthor();
        var genre = new Genre(null, "Comedy", List.of(book.getId()), new ArrayList<>(book.getAuthors()));

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
    void deleteGenreById() {
        var genre = new Genre(null, "Dramatic", null, null);

        val dbGenre = service.create(genre);

        assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());

        service.delete(dbGenre.getId());

        assertFalse(service.getRepository().findById(dbGenre.getId()).isEmpty());
    }

    @Test
    @DisplayName("Delete genre with book by id")
    void deleteGenreWithBookById() {
        val book = createBookWithAuthor();
        var genre = new Genre(null, "Comedy", List.of(book.getId()), new ArrayList<>(book.getAuthors()));

        val dbGenre = service.create(genre);

        assertNotNull(dbGenre);
        assertEquals(genre.getTitle(), dbGenre.getTitle());

        service.delete(dbGenre.getId());

        assertFalse(service.getRepository().findById(dbGenre.getId()).isEmpty());
    }

    @Test
    @DisplayName("Delete genres by id")
    void deleteGenresById() {
        val book = createBookWithAuthor();
        var genres = List.of(
                new Genre(null, "Comedy", List.of(book.getId()), book.getAuthors().stream().toList()),
                new Genre(null, "Indie", List.of(book.getId()), book.getAuthors().stream().toList()),
                new Genre(null, "Fighting", List.of(book.getId()), book.getAuthors().stream().toList())
        );


        val dbGenre = genres.stream().map(genre -> service.create(genre)).toList();

        service.read().forEach(genre -> service.delete(genre.getId()));

        assertFalse(service.getRepository().findAllById(dbGenre.stream()
                .map(Genre::getId).toList())
                .isEmpty());
    }

    private Book createBook() {
        val publisher = publisherService.create(new Publisher("Marvel"));
        return bookService.create(new Book(BigDecimal.ZERO, publisher.getId(), "Earth", new Date()));
    }

    private Book createBookWithAuthor() {
        val publisher = publisherService.create(new Publisher("Marvel"));
        val author = authorService.create(new Author("Sanzhar", "Zhanibekov", new Date()));
        val book = new Book(BigDecimal.ZERO, publisher.getId(), "Earth", new Date());
        book.setAuthors(Set.of(author.getId()));
        book.setPublisher(publisher.getId());
        return  bookService.create(book);
    }

    @AfterAll
    static void cleanup() {
        var service = ApplicationContextProvider.getApplicationContext()
                .getBean(GenreServiceImpl.class);
        var publisherService = ApplicationContextProvider.getApplicationContext()
                .getBean(PublisherServiceImpl.class);
        var bookService = ApplicationContextProvider.getApplicationContext()
                .getBean(BookServiceImpl.class);
        var authorService = ApplicationContextProvider.getApplicationContext()
                .getBean(AuthorServiceImpl.class);

        service.read().forEach(genre -> service.delete(genre.getId()));
        bookService.read().forEach(book -> bookService.delete(book.getId()));
        authorService.read().forEach(author -> authorService.delete(author.getId()));
        publisherService.read().forEach(publisher -> publisherService.delete(publisher.getId()));
    }
}
