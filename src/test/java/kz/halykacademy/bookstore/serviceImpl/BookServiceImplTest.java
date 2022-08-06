package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
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
class BookServiceImplTest extends ProviderTestTools {

    @Autowired
    private BookServiceImpl provider;
    @Autowired
    private PublisherServiceImpl publisherServiceImpl;;
    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @BeforeEach
    void setUp() {
        assertNotNull(provider, "Provider did not autowired in test");
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

        var dbBook = provider.create(book);

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

        var dbBooks = provider.create(books);

        assertNotNull(dbBooks);
    }

    @Test
    @DisplayName("Save and flush book")
    void saveAndFlush() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = provider.create(book);

        assertNotNull(dbBook);

        dbBook.setNumberOfPage(150);

        dbBook = provider.update(dbBook);

        assertNotNull(dbBook);
        assertEquals(150, dbBook.getNumberOfPage());

    }

    @Test
    @DisplayName("Remove all books")
    void removeAll() {
        provider.deleteAll();

        var books = provider.getAll();

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("Remove book by id")
    void removeById() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = provider.create(book);

        assertNotNull(dbBook);

        provider.delete(dbBook.getId());

        assertNull(provider.findById(dbBook.getId()));
    }

    @Test
    @DisplayName("Find book by id")
    void findById() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = provider.create(book);

        assertNotNull(dbBook);

        var secondDbBook = provider.findById(dbBook.getId());

        assertNotNull(secondDbBook);
        assertEquals(dbBook.getId(), secondDbBook.getId());
    }

    @Test
    @DisplayName("Get all books")
    void getAll() {
        Book book = new Book(null, new BigDecimal(990), null, null, "Title 4", 100, new Date());

        var dbBook = provider.create(book);

        assertNotNull(dbBook);

        var allBooks = provider.getAll();

        assertNotNull(allBooks);
    }

    @Test
    @DisplayName("Save book with publisher, but publisher didn't save")
    void saveWithOtherEntity() {
        var publisher = publisherServiceImpl.create(new Publisher("Publisher title", null));

        var book = new Book(null, new BigDecimal(990), null, publisher, "Title 4", 100, new Date());

        var dbBook = provider.create(book);

        assertNotNull(dbBook);
        assertNotNull(dbBook.getPublisher());
//
//        var dbPublisher = publisherProvider.findById(dbBook.getPublisher().getId());
//
//        assertNotNull(dbPublisher.getBookList());
//        log.info(dbPublisher.getBookList().toString());
    }
}