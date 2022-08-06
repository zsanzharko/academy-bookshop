package kz.halykacademy.bookstore.service;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.serviceImpl.BookServiceImpl;
import kz.halykacademy.bookstore.serviceImpl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static kz.halykacademy.bookstore.service.ServiceTestTools.deleteAllEntities;

@SpringBootTest
@Slf4j
class PublisherServiceImplTest {

    @Autowired
    private PublisherServiceImpl service;

    @Autowired
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(service, "Provider did not autowired in test");
    }

    @AfterAll
    static void cleanup() {
        var service = ApplicationContextProvider.getApplicationContext().getBean(PublisherServiceImpl.class);
        service.deleteAll();
    }

    @Test
    @DisplayName("Save publisher")
    void save() {
        // STEP 1
        // save without books

        // clean database
        deleteAllEntities(List.of(service));

        // Create object to save
        var publisher = new Publisher("Publisher test save");

        // operation
        var dbPublisher = service.create(publisher);

        // assertions
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(publisher.getTitle(), dbPublisher.getTitle());

        // STEP 2
        // save with books

        var publisherWithBooks = new Publisher("Publisher with books");

        // pre-operation

        publisherWithBooks = service.create(publisher);

        var books = List.of(
                new Book(new BigDecimal(990), publisherWithBooks.getId(), "Book to save with publisher 1", new Date()),
                new Book(new BigDecimal(990), publisherWithBooks.getId(), "Book to save with publisher 2", new Date()),
                new Book(new BigDecimal(990), publisherWithBooks.getId(), "Book to save with publisher 3", new Date()),
                new Book(new BigDecimal(990), publisherWithBooks.getId(), "Book to save with publisher 4", new Date())
        );

        var dbBook = bookService.create(books);

        publisherWithBooks.setBooks(dbBook.stream().map(Book::getId).toList());

        // operation
        var dbPublisherWithBooks = service.create(publisherWithBooks);

        // assertions
        Assertions.assertNotNull(dbPublisherWithBooks);
        Assertions.assertEquals(publisherWithBooks.getTitle(), dbPublisherWithBooks.getTitle());
        Assertions.assertNotNull(dbPublisherWithBooks.getBooks());
    }

    @Test
    @DisplayName("Save all publishers")
    void saveAll() {
        // STEP 1
        // save without books

        // clean database
        deleteAllEntities(List.of(service));


        // Create object to save
        var publishersTestSave = List.of(
                new Publisher("Publisher test save 1"),
                new Publisher("Publisher test save 2"),
                new Publisher("Publisher test save 3"),
                new Publisher("Publisher test save 4")
        );

        // operation
        var dbPublishers = service.create(publishersTestSave);

        var publishersListTitles = publishersTestSave.stream().map(Publisher::getTitle).toList();
        var dbPublishersListTitles = dbPublishers.stream().map(Publisher::getTitle).toList();

        // assertions
        Assertions.assertNotNull(dbPublishers);
        Assertions.assertEquals(publishersListTitles, dbPublishersListTitles);

        // STEP 2
        // save with books

        // Create object to save
        var publishersTestSave2 = List.of(
                new Publisher("Publisher test save 5"),
                new Publisher("Publisher test save 6")
                );

        var books1 = List.of(
                new Book(new BigDecimal(990), publishersTestSave2.get(0).getId(), "Book to save with publisher 5", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(0).getId(), "Book to save with publisher 6", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(0).getId(), "Book to save with publisher 7", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(0).getId(), "Book to save with publisher 8", new Date())
        );
        var books2 = List.of(
                new Book(new BigDecimal(990), publishersTestSave2.get(1).getId(), "Book to save with publisher 9", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(1).getId(), "Book to save with publisher 10", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(1).getId(), "Book to save with publisher 11", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(1).getId(), "Book to save with publisher 12", new Date())
        );

        publishersTestSave2.get(0).setBooks(books1.stream().map(Book::getId).toList());
        publishersTestSave2.get(1).setBooks(books2.stream().map(Book::getId).toList());

        // operation
        var dbPublishersTestSave2 = service.create(publishersTestSave2);

        // assertion
        Assertions.assertNotNull(dbPublishersTestSave2);
        Assertions.assertNotNull(dbPublishersTestSave2.get(0));
        Assertions.assertNotNull(dbPublishersTestSave2.get(1));

        Assertions.assertEquals(publishersTestSave2.get(0).getTitle(), dbPublishersTestSave2.get(0).getTitle());
        Assertions.assertEquals(publishersTestSave2.get(1).getTitle(), dbPublishersTestSave2.get(1).getTitle());

        Assertions.assertNotNull(dbPublishersTestSave2.get(0).getBooks());
        Assertions.assertNotNull(dbPublishersTestSave2.get(1).getBooks());

    }

    @Test
    @DisplayName("Save and flush publisher")
    void saveAndFlush() {
        // pre req
        final String changeTitle = "Change title on publisher";

        // STEP 1
        // update  without books

        // clean database
        deleteAllEntities(List.of(service));

        // Create object to save
        var publisher = new Publisher("Publisher test flush update");

        var dbPublisher = service.create(publisher);

        dbPublisher.setTitle(changeTitle);



        // operation
        dbPublisher = service.update(dbPublisher);

        // assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(changeTitle, dbPublisher.getTitle());

        // STEP 2
        // update with books


        // Create object to save
        var publisher2 = new Publisher("Publisher 2 test flush update");
//        publisher2.setBooks(List.of(new Book(
//                new BigDecimal(1990), publisher2.getId(), "Old Book in Publisher", new Date())));

        var dbPublisher2 = service.create(publisher2);

//        // operation
//        final var changeBooks = List.of(
//                new Book(new BigDecimal(990), dbPublisher2, "Change Book in Publisher", new Date()));
//
//
//        dbPublisher2.setBooks(changeBooks);
//
//        dbPublisher2 = service.update(dbPublisher2);
//
//        // assertion
//
//        Assertions.assertNotNull(dbPublisher2);
//        Assertions.assertNotNull(dbPublisher2.getBooks());
//        Assertions.assertEquals(
//                changeBooks.stream().toList().get(0).getTitle(),
//                dbPublisher2.getBooks().stream().toList().get(0).getTitle());

        // clean database
        deleteAllEntities(List.of(service));
    }

    @Test
    @DisplayName("Remove all publishers")
    void removeAll() {
        // clean database
        deleteAllEntities(List.of(service));

        final String testTitle1 = ("Publisher test save1");
        final String testTitle2 = ("Publisher test save2");
        final String testTitle3 = ("Publisher test save3");

        // operation
        var dbPublisher1 = service.create(new Publisher(testTitle1));
        var dbPublisher2 = service.create(new Publisher(testTitle2));
        var dbPublisher3 = service.create(new Publisher(testTitle3));


        service.deleteAll();

        Assertions.assertNull(service.read(dbPublisher1.getId()));
        Assertions.assertNull(service.read(dbPublisher2.getId()));
        Assertions.assertNull(service.read(dbPublisher3.getId()));

        // clean database
        deleteAllEntities(List.of(service));
    }

    @Test
    @DisplayName("Remove publisher by id")
    void removeById() {
        // clean database
        deleteAllEntities(List.of(service));

        final String testTitle1 = ("Publisher test save1");

        // operation
        var dbPublisher1 = service.create(new Publisher(testTitle1));

        service.delete(dbPublisher1.getId());

        // clean database
        deleteAllEntities(List.of(service));
    }

    @Test
    @DisplayName("Find publisher by id")
    void findById() {
        // clean database
        deleteAllEntities(List.of(service));

        final String testTitle1 = ("Publisher test find by id");

        // operation
        var dbPublisher1 = service.create(new Publisher(testTitle1));

        var entityById = service.read(dbPublisher1.getId());

        // assertion
        Assertions.assertNotNull(entityById);
        Assertions.assertEquals(dbPublisher1, entityById);

        // clean database
        deleteAllEntities(List.of(service));
    }

    @Test
    @DisplayName("Get all publishers")
    void getAll() {
        // clean database
        deleteAllEntities(List.of(service));

        final String testTitle1 = ("Publisher test save1");
        final String testTitle2 = ("Publisher test save2");
        final String testTitle3 = ("Publisher test save3");

        var dbPublisher1 = service.create(new Publisher(testTitle1));
        var dbPublisher2 = service.create(new Publisher(testTitle2));
        var dbPublisher3 = service.create(new Publisher(testTitle3));

        // operation
        List<Publisher> publishers = service.read();

        Assertions.assertNotNull(publishers);
        Assertions.assertEquals(List.of(dbPublisher3, dbPublisher2, dbPublisher1), publishers);

        // clean database
        deleteAllEntities(List.of(service));
    }
}