package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static kz.halykacademy.bookstore.serviceImpl.ProviderTestTools.deleteAllEntities;

@SpringBootTest
@Slf4j
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl provider;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(provider, "Provider did not autowired in test");
    }

    @Test
    @DisplayName("Save author")
    void save() {
        // STEP 1
        // save without books

        // clean database
        deleteAllEntities(List.of(provider));

        // Create object to save
        var author = new Author("Sanzhar", "Zhanibekov", new Date());

        // operation
        var dbAuthor = provider.create(author);

        // assertions
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(author.getName(), dbAuthor.getName());

//        // STEP 2
//        // save with books
//
//        // Create object to save
//        var authorWithBook = new Author("Sanzhar", "Not Zhanibekov", new Date());
//        var publisher = new Publisher("Publisher to author to book, test Save author");
//        var title = "Book to save with author 1";
//
//        final var publisherProvider = ApplicationContextProvider.getApplicationContext().getBean(PublisherServiceImpl.class);
//        publisher = publisherProvider.create(publisher);
//
//        var book = new Book(new BigDecimal(990), publisher, title, new Date());
//        // operation
//        authorWithBook.setWrittenBooks(Set.of(book));
//
//        var dbAuthorWithBook = provider.create(authorWithBook);
//
//        // assertion
//        Assertions.assertNotNull(dbAuthorWithBook);
//        Assertions.assertNotNull(dbAuthorWithBook.getWrittenBooks());
//        Assertions.assertEquals(title, dbAuthorWithBook.getWrittenBooks().stream().toList().get(0).getTitle());
    }

    @Test
    @DisplayName("Save all authors")
    void saveAll() {
    }

    @Test
    @DisplayName("Save and flush author")
    void saveAndFlush() {
    }

    @Test
    @DisplayName("Remove all authors")
    void removeAll() {
    }

    @Test
    @DisplayName("Remove author by id")
    void removeById() {
    }

    @Test
    @DisplayName("Find author by id")
    void findById() {
    }

    @Test
    @DisplayName("Get all authors")
    void getAll() {
    }
}