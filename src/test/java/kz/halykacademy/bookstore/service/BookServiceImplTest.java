package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.serviceImpl.AuthorServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BookServiceImplTest extends ServiceTestTools {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private PublisherServiceImpl publisherService;;
    @Autowired
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        assertNotNull(bookService, "Provider did not autowired in test");
    }


    @AfterAll
    public static void clean() {
        val service = ApplicationContextProvider.getApplicationContext().getBean(BookServiceImpl.class);
        val publisherService = ApplicationContextProvider.getApplicationContext().getBean(PublisherServiceImpl.class);
        publisherService.deleteAll();
        service.deleteAll();
    }

    @Test
    @DisplayName("Save book")
    void save() {
        val publisher = publisherService.create(new Publisher("Some Publisher"));

        Book book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);
    }

    @Test
    @DisplayName("Save book with author")
    void saveWithAuthor() {
        val publisher = publisherService.create(new Publisher("Some Publisher"));

        Book book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title", 100, new Date());

        var author = authorService.create(new Author("Name", "Surname", new Date()));
        book.setAuthors(Set.of(author.getId()));

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);
        assertNotNull(dbBook.getAuthors());
        assertEquals(1, dbBook.getAuthors().size());
    }

    @Test
    @DisplayName("Save all books")
    void saveAll() {
        val publisher = publisherService.create(new Publisher("Some Publisher with all books"));

        var books = List.of(
                new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 1", 100, new Date()),
                new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 2", 100, new Date()),
                new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 3", 100, new Date())
        );

        var dbBooks = bookService.create(books);

        assertNotNull(dbBooks);
    }

    @Test
    @DisplayName("Save and flush book")
    void saveAndFlush() {
        val publisher = publisherService.create(new Publisher("Some Publisher with updating data book"));
        Book book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        dbBook.setNumberOfPage(150);

        dbBook = bookService.update(dbBook);

        assertNotNull(dbBook);
        assertEquals(150, dbBook.getNumberOfPage());
    }

    @Test
    @DisplayName("Remove all books")
    void removeAll() {
        bookService.deleteAll();

        var books = bookService.read();

        assertTrue(books.isEmpty());
    }

    @Test
    @DisplayName("Remove book by id")
    void removeById() {
        val publisher = publisherService.create(new Publisher("Some Publisher to remove book"));
        Book book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        bookService.delete(dbBook.getId());

        assertNull(bookService.read(dbBook.getId()));
    }

    @Test
    @DisplayName("Find book by id")
    void findById() {
        val publisher = publisherService.create(new Publisher("Some Publisher to find a book"));
        Book book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        var secondDbBook = bookService.read(dbBook.getId());

        assertNotNull(secondDbBook);
        assertEquals(dbBook.getId(), secondDbBook.getId());
    }

    @Test
    @DisplayName("Get all books")
    void getAll() {
        val publisher = publisherService.create(new Publisher("Some Publisher to get all books"));
        Book book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        var allBooks = bookService.read();

        assertNotNull(allBooks);
    }
}