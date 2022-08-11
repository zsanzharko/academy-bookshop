package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class PublisherServiceImplTest {

    @Autowired
    private PublisherServiceImpl service;

    @Autowired
    private BookServiceImpl bookService;

    @BeforeEach
    void clean() {
        service.read().stream()
                .map(Publisher::getId)
                .forEach(bookService::delete);
        bookService.read().stream()
                .map(Book::getId)
                .forEach(bookService::delete);
    }

    @Test
    @DisplayName("Test service")
    void setUp() {
        assertNotNull(service, "Provider did not autowired in test");
    }

    @Test
    @DisplayName("Save publisher")
    void save() {
        // Create object to save
        var publisher = new Publisher("Publisher test save");

        // operation
        var dbPublisher = service.create(publisher);

        // assertions
        assertNotNull(dbPublisher);
        assertEquals(publisher.getTitle(), dbPublisher.getTitle());
    }

    @Test
    @DisplayName("Save publisher with books")
    void saveWithBooks() {
        var publisher = new Publisher("Publisher with books");

        // pre-operation
        publisher = service.create(publisher);

        var books = List.of(
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 1", new Date()),
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 2", new Date()),
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 3", new Date()),
                new Book(new BigDecimal(990), publisher.getId(), "Book to save with publisher 4", new Date())
        );

        var dbBook = books.stream().map(book -> bookService.create(book)).toList();

        publisher.setBooks(dbBook.stream().map(Book::getId).toList());

        // operation
        var dbPublisherWithBooks = service.create(publisher);

        // assertions
        assertNotNull(dbPublisherWithBooks);
        assertEquals(publisher.getTitle(), dbPublisherWithBooks.getTitle());
        assertNotNull(dbPublisherWithBooks.getBooks());
    }

    @Test
    @DisplayName("Save all publishers")
    void saveAllWithoutBooks() {
        // Create object to save
        var publishersTestSave = List.of(
                new Publisher("Publisher test save 1"),
                new Publisher("Publisher test save 2"),
                new Publisher("Publisher test save 3"),
                new Publisher("Publisher test save 4")
        );

        // operation
        var dbPublishers = publishersTestSave.stream().map(publisher -> service.create(publisher)).toList();

        var publishersListTitles = publishersTestSave.stream().map(Publisher::getTitle).toList();
        var dbPublishersListTitles = dbPublishers.stream().map(Publisher::getTitle).toList();

        // assertions
        assertNotNull(dbPublishers);
        assertEquals(publishersListTitles, dbPublishersListTitles);
    }

    @Test
    @DisplayName("Save all publishers with books ")
    void saveAllWithBooks() {
        // Create object to save
        var publishers = List.of(
                new Publisher("Publisher test save 5"),
                new Publisher("Publisher test save 6")
        );

        var books1 = List.of(
                new Book(new BigDecimal(990), publishers.get(0).getId(), "Book to save with publisher 5", new Date()),
                new Book(new BigDecimal(990), publishers.get(0).getId(), "Book to save with publisher 6", new Date()),
                new Book(new BigDecimal(990), publishers.get(0).getId(), "Book to save with publisher 7", new Date()),
                new Book(new BigDecimal(990), publishers.get(0).getId(), "Book to save with publisher 8", new Date())
        );
        var books2 = List.of(
                new Book(new BigDecimal(990), publishers.get(1).getId(), "Book to save with publisher 9", new Date()),
                new Book(new BigDecimal(990), publishers.get(1).getId(), "Book to save with publisher 10", new Date()),
                new Book(new BigDecimal(990), publishers.get(1).getId(), "Book to save with publisher 11", new Date()),
                new Book(new BigDecimal(990), publishers.get(1).getId(), "Book to save with publisher 12", new Date())
        );

        publishers.get(0).setBooks(books1.stream().map(Book::getId).toList());
        publishers.get(1).setBooks(books2.stream().map(Book::getId).toList());

        // operation
        var dbPublishersTestSave2 = publishers.stream()
                .map(publisher -> service.create(publisher)).toList();

        // assertion
        assertNotNull(dbPublishersTestSave2);
        assertNotNull(dbPublishersTestSave2.get(0));
        assertNotNull(dbPublishersTestSave2.get(1));

        assertEquals(publishers.get(0).getTitle(), dbPublishersTestSave2.get(0).getTitle());
        assertEquals(publishers.get(1).getTitle(), dbPublishersTestSave2.get(1).getTitle());

        assertNotNull(dbPublishersTestSave2.get(0).getBooks());
        assertNotNull(dbPublishersTestSave2.get(1).getBooks());
    }

    @Test
    @DisplayName("Save and flush publisher")
    void saveAndFlush() {
        // pre req
        final String changeTitle = "Change title on publisher";

        // STEP 1
        // update  without books

        // Create object to save
        var publisher = new Publisher("Publisher test flush update");

        var dbPublisher = service.create(publisher);

        dbPublisher.setTitle(changeTitle);

        // operation
        dbPublisher = service.update(dbPublisher);

        // assertion
        assertNotNull(dbPublisher);
        assertEquals(changeTitle, dbPublisher.getTitle());
    }

    @Test
    @DisplayName("Save and flush publisher with books")
    void saveAndFlushWithBooks() {
        // Create object to save
        var publisher = service.create(new Publisher("Publisher 2 test flush update"));

        Stream.of(new Book(
                        new BigDecimal(1990), publisher.getId(), "Old Book in Publisher", new Date()))
                .forEach(book -> bookService.create(book));

        publisher = service.read(publisher.getId());

        assertNotNull(publisher.getBooks());
        assertEquals(1, publisher.getBooks().size());

//        publisher.setBooks(books.stream().map(Book::getId).toList()); // set old books


        // operation
        final var changeBooks = List.of(
                new Book(new BigDecimal(990), publisher.getId(), "Change Book in Publisher", new Date()));


        publisher.setBooks(changeBooks.stream().map(Book::getId).toList());

        publisher = service.update(publisher);

        // assertion

        assertNotNull(publisher);
        assertNotNull(publisher.getBooks());
        assertEquals(changeBooks.size(), publisher.getBooks().size());
    }

    @Test
    @DisplayName("Remove all publishers")
    void removeAll() {

        final String testTitle1 = ("Publisher test save1");
        final String testTitle2 = ("Publisher test save2");
        final String testTitle3 = ("Publisher test save3");

        // operation
        var dbPublisher1 = service.create(new Publisher(testTitle1));
        var dbPublisher2 = service.create(new Publisher(testTitle2));
        var dbPublisher3 = service.create(new Publisher(testTitle3));


        service.read().stream()
                .map(Publisher::getId)
                .forEach(bookService::delete);

        Assertions.assertNull(service.read(dbPublisher1.getId()));
        Assertions.assertNull(service.read(dbPublisher2.getId()));
        Assertions.assertNull(service.read(dbPublisher3.getId()));
    }

    @Test
    @DisplayName("Remove publisher by id")
    void removeById() {

        final String testTitle1 = ("Publisher test save1");

        // operation
        var dbPublisher1 = service.create(new Publisher(testTitle1));

        service.delete(dbPublisher1.getId());
    }

    @Test
    @DisplayName("Find publisher by id")
    void findById() {

        final String testTitle1 = ("Publisher test find by id");

        // operation
        var dbPublisher1 = service.create(new Publisher(testTitle1));

        var entityById = service.read(dbPublisher1.getId());

        // assertion
        assertNotNull(entityById);
        assertEquals(dbPublisher1, entityById);
    }

    @Test
    @DisplayName("Get all publishers")
    void getAll() {

        final String testTitle1 = ("Publisher test save1");
        final String testTitle2 = ("Publisher test save2");
        final String testTitle3 = ("Publisher test save3");

        service.create(new Publisher(testTitle1));
        service.create(new Publisher(testTitle2));
        service.create(new Publisher(testTitle3));

        // operation
        List<Publisher> publishers = service.read();

        assertNotNull(publishers);
    }
}