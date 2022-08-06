package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.serviceImpl.AuthorServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
        var provider = ApplicationContextProvider.getApplicationContext().getBean(BookServiceImpl.class);
        provider.deleteAll();
    }
    @Test
    @DisplayName("Save book")
    void save() {

        Book book = new Book(null, new BigDecimal(990), null, null, "Title", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);
    }

    @Test
    @DisplayName("Save all books")
    void saveAll() {
        var books = List.of(
                new Book(null, new BigDecimal(990), null, null, "Title 1", 100, new Date()),
                new Book(null, new BigDecimal(990), null, null, "Title 2", 100, new Date()),
                new Book(null, new BigDecimal(990), null, null, "Title 3", 100, new Date())
        );

        var dbBooks = bookService.create(books);

        assertNotNull(dbBooks);
    }

    @Test
    @DisplayName("Save and flush book")
    void saveAndFlush() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

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

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("Remove book by id")
    void removeById() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        bookService.delete(dbBook.getId());

        assertNull(bookService.read(dbBook.getId()));
    }

    @Test
    @DisplayName("Find book by id")
    void findById() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        var secondDbBook = bookService.read(dbBook.getId());

        assertNotNull(secondDbBook);
        assertEquals(dbBook.getId(), secondDbBook.getId());
    }

    @Test
    @DisplayName("Get all books")
    void getAll() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);

        var allBooks = bookService.read();

        assertNotNull(allBooks);
    }

    @Test
    @DisplayName("Save book with publisher, but publisher didn't save")
    void saveWithOtherEntity() {
        var publisher = publisherService.create(new Publisher("Publisher title", null));

        var book = new Book(null, new BigDecimal(990), null, publisher.getId(), "Title 4", 100, new Date());

        var dbBook = bookService.create(book);

        assertNotNull(dbBook);
        assertNotNull(dbBook.getPublisher());
//
//        var dbPublisher = publisherProvider.findById(dbBook.getPublisher().getId());
//
//        assertNotNull(dbPublisher.getBooks());
//        log.info(dbPublisher.getBooks().toString());
    }
}