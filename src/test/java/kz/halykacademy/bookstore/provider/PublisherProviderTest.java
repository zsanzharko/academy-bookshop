package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static kz.halykacademy.bookstore.provider.ProviderTestTools.deleteAllEntities;

@SpringBootTest
@Slf4j
class PublisherProviderTest {

    @Autowired
    private PublisherProvider provider;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(provider, "Provider did not autowired in test");
    }

    @Test
    @DisplayName("Save publisher")
    void save() {
        // STEP 1
        // save without books

        // clean database
        deleteAllEntities(List.of(provider));

        // Create object to save
        var publisher = new Publisher("Publisher test save");

        // operation
        var dbPublisher = provider.create(publisher);

        // assertions
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(publisher.getTitle(), dbPublisher.getTitle());

        // STEP 2
        // save with books

        var publisherWithBooks = new Publisher("Publisher with books");
        var books = Set.of(
                new Book(new BigDecimal(990), publisherWithBooks, "Book to save with publisher 1", new Date()),
                new Book(new BigDecimal(990), publisherWithBooks, "Book to save with publisher 2", new Date()),
                new Book(new BigDecimal(990), publisherWithBooks, "Book to save with publisher 3", new Date()),
                new Book(new BigDecimal(990), publisherWithBooks, "Book to save with publisher 4", new Date())
        );
        publisherWithBooks.setBookList(books);

        // operation
        var dbPublisherWithBooks = provider.create(publisherWithBooks);

        // assertions
        Assertions.assertNotNull(dbPublisherWithBooks);
        Assertions.assertEquals(publisherWithBooks.getTitle(), dbPublisherWithBooks.getTitle());

        for (var book : publisherWithBooks.getBookList()) {
            Assertions.assertNotNull(dbPublisherWithBooks.getBookList());
            for (var dbBook : dbPublisherWithBooks.getBookList())
                if (book.getTitle().equals(dbBook.getTitle()))
                    book.setId(dbBook.getId());
        }
        //todo sort list to check books dataset

        // assertions
        Assertions.assertNotNull(dbPublisherWithBooks.getBookList());
    }

    @Test
    @DisplayName("Save all publishers")
    void saveAll() {
        // STEP 1
        // save without books

        // clean database
        deleteAllEntities(List.of(provider));


        // Create object to save
        var publishersTestSave = List.of(
                new Publisher("Publisher test save 1"),
                new Publisher("Publisher test save 2"),
                new Publisher("Publisher test save 3"),
                new Publisher("Publisher test save 4")
        );

        // operation
        var dbPublishers = provider.create(publishersTestSave);

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

        var books1 = Set.of(
                new Book(new BigDecimal(990), publishersTestSave2.get(0), "Book to save with publisher 5", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(0), "Book to save with publisher 6", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(0), "Book to save with publisher 7", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(0), "Book to save with publisher 8", new Date())
        );
        var books2 = Set.of(
                new Book(new BigDecimal(990), publishersTestSave2.get(1), "Book to save with publisher 9", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(1), "Book to save with publisher 10", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(1), "Book to save with publisher 11", new Date()),
                new Book(new BigDecimal(990), publishersTestSave2.get(1), "Book to save with publisher 12", new Date())
        );

        publishersTestSave2.get(0).setBookList(books1);
        publishersTestSave2.get(1).setBookList(books2);

        // operation
        var dbPublishersTestSave2 = provider.create(publishersTestSave2);

        // assertion
        Assertions.assertNotNull(dbPublishersTestSave2);
        Assertions.assertNotNull(dbPublishersTestSave2.get(0));
        Assertions.assertNotNull(dbPublishersTestSave2.get(1));

        Assertions.assertEquals(publishersTestSave2.get(0).getTitle(), dbPublishersTestSave2.get(0).getTitle());
        Assertions.assertEquals(publishersTestSave2.get(1).getTitle(), dbPublishersTestSave2.get(1).getTitle());

        Assertions.assertNotNull(dbPublishersTestSave2.get(0).getBookList());
        Assertions.assertNotNull(dbPublishersTestSave2.get(1).getBookList());

    }

    @Test
    @DisplayName("Save and flush publisher")
    void saveAndFlush() {
        // pre req
        final String changeTitle = "Change title on publisher";

        // STEP 1
        // update  without books

        // clean database
        deleteAllEntities(List.of(provider));

        // Create object to save
        var publisher = new Publisher("Publisher test flush update");

        var dbPublisher = provider.create(publisher);

        dbPublisher.setTitle(changeTitle);

        // operation
        dbPublisher = provider.saveAndFlush(dbPublisher);

        // assertion
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(changeTitle, dbPublisher.getTitle());

        // STEP 2
        // update with books


        // Create object to save
        var publisher2 = new Publisher("Publisher 2 test flush update");
        publisher2.setBookList(Set.of(new Book(
                new BigDecimal(1990), publisher2, "Old Book in Publisher", new Date())));

        var dbPublisher2 = provider.create(publisher2);

        // operation
        final var changeBooks = Set.of(
                new Book(new BigDecimal(990), dbPublisher2, "Change Book in Publisher", new Date()));


        dbPublisher2.setBookList(changeBooks);

        dbPublisher2 = provider.update(dbPublisher2);

        // assertion

        Assertions.assertNotNull(dbPublisher2);
        Assertions.assertNotNull(dbPublisher2.getBookList());
        Assertions.assertEquals(
                changeBooks.stream().toList().get(0).getTitle(),
                dbPublisher2.getBookList().stream().toList().get(0).getTitle());

        // clean database
        deleteAllEntities(List.of(provider));
    }

    @Test
    @DisplayName("Remove all publishers")
    void removeAll() {
        // clean database
        deleteAllEntities(List.of(provider));

        final String testTitle1 = ("Publisher test save1");
        final String testTitle2 = ("Publisher test save2");
        final String testTitle3 = ("Publisher test save3");

        // operation
        var dbPublisher1 = provider.create(new Publisher(testTitle1));
        var dbPublisher2 = provider.create(new Publisher(testTitle2));
        var dbPublisher3 = provider.create(new Publisher(testTitle3));


        provider.removeAll();

        Assertions.assertNull(provider.findById(dbPublisher1.getId()));
        Assertions.assertNull(provider.findById(dbPublisher2.getId()));
        Assertions.assertNull(provider.findById(dbPublisher3.getId()));

        // clean database
        deleteAllEntities(List.of(provider));
    }

    @Test
    @DisplayName("Remove publisher by id")
    void removeById() {
        // clean database
        deleteAllEntities(List.of(provider));

        final String testTitle1 = ("Publisher test save1");

        // operation
        var dbPublisher1 = provider.create(new Publisher(testTitle1));

        provider.removeById(dbPublisher1.getId());

        // clean database
        deleteAllEntities(List.of(provider));
    }

    @Test
    @DisplayName("Find publisher by id")
    void findById() {
        // clean database
        deleteAllEntities(List.of(provider));

        final String testTitle1 = ("Publisher test find by id");

        // operation
        var dbPublisher1 = provider.create(new Publisher(testTitle1, new HashSet<>()));

        var entityById = provider.findById(dbPublisher1.getId());

        // assertion
        Assertions.assertNotNull(entityById);
        Assertions.assertEquals(dbPublisher1, entityById);

        // clean database
        deleteAllEntities(List.of(provider));
    }

    @Test
    @DisplayName("Get all publishers")
    void getAll() {
        // clean database
        deleteAllEntities(List.of(provider));

        final String testTitle1 = ("Publisher test save1");
        final String testTitle2 = ("Publisher test save2");
        final String testTitle3 = ("Publisher test save3");

        var dbPublisher1 = provider.create(new Publisher(testTitle1, new HashSet<>()));
        var dbPublisher2 = provider.create(new Publisher(testTitle2, new HashSet<>()));
        var dbPublisher3 = provider.create(new Publisher(testTitle3, new HashSet<>()));

        // operation
        List<Publisher> publishers = provider.getAll();

        Assertions.assertNotNull(publishers);
        Assertions.assertEquals(List.of(dbPublisher3, dbPublisher2, dbPublisher1), publishers);

        // clean database
        deleteAllEntities(List.of(provider));
    }
}