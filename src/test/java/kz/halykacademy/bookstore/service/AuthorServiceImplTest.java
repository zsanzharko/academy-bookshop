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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl service;
    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setUp() {
        assertNotNull(service, "Provider did not autowired in test");
        bookService.read().stream()
                .map(Book::getId)
                .forEach(bookService::delete);
    }

    @AfterAll
    public static void clean() {
        val bookService = ApplicationContextProvider.getApplicationContext().getBean(BookServiceImpl.class);
        val service = ApplicationContextProvider.getApplicationContext().getBean(AuthorServiceImpl.class);
        service.read().stream()
                .map(Author::getId)
                .forEach(service::delete);          // deleting all in authors
        bookService.read().stream()
                .map(Book::getId)
                .forEach(bookService::delete);      // deleting all in books
    }


    @Test
    @DisplayName("Save author without book")
    void saveWithoutBook() {
        var author = new Author("Sanzhar", "Zhanibekov", new Date());

        var dbAuthor = service.create(author);

        assertNotNull(dbAuthor);
        Assertions.assertEquals(author.getName(), dbAuthor.getName());
    }

    @Test
    @DisplayName("Save author with book")
    void saveWithBook() {
        var authorWithBook = new Author("Sanzhar", "Not Zhanibekov", new Date());
        var publisher = publisherService.create(new Publisher("Publisher author to book"));
        var title = "Book to save with author 1";

        var book = bookService.create(new Book(new BigDecimal(990), publisher.getId(), title, new Date()));

        authorWithBook.setWrittenBooks(Set.of(book.getId()));

        var dbAuthorWithBook = service.create(authorWithBook);

        // assertion
        assertNotNull(dbAuthorWithBook);
        assertNotNull(dbAuthorWithBook.getWrittenBooks());
        Assertions.assertEquals(title,
                bookService.read(dbAuthorWithBook.getWrittenBooks()
                                .stream().toList().get(0))
                        .getTitle());
    }

    @Test
    @DisplayName("Save all authors without books")
    void saveAllWithoutBooks() {
        val authors = List.of(
                new Author("Sanzhar", "Zhanibekov", new Date()),
                new Author("Another Sanzhar", "Another Zhanibekov", new Date()),
                new Author("Another Another Sanzhar", "Another Another Zhanibekov", new Date())
        );

        var dbAuthors = authors.stream().map(author -> service.create(author)).toList();

        assertNotNull(dbAuthors);
        assertEquals(authors.size(), dbAuthors.size());
    }

    @Test
    @DisplayName("Save all authors with books")
    void saveAllWithBooks() {
        val authors = List.of(
                new Author("Sanzhar", "Zhanibekov", new Date()),
                new Author("Another Sanzhar", "Another Zhanibekov", new Date()),
                new Author("Another Another Sanzhar", "Another Another Zhanibekov", new Date())
        );

        val publisher = publisherService.create(new Publisher("Publisher 255"));

        val books = Stream.of(
                new Book(new BigDecimal(1990), publisher.getId(), "title", new Date()),
                new Book(new BigDecimal(1990), publisher.getId(), "title", new Date()),
                new Book(new BigDecimal(1990), publisher.getId(), "title", new Date())
        ).map(book -> bookService.create(book)).toList();

        for (int i = 0; i < 3; i++)
            authors.get(i).setWrittenBooks(Set.of(books.get(i).getId()));

        val dbAuthorsWithBooks = authors.stream().map(author -> service.create(author)).toList();

        assertNotNull(dbAuthorsWithBooks);
        assertEquals(authors.size(), dbAuthorsWithBooks.size());
        for (int i = 0; i < dbAuthorsWithBooks.size(); i++) {
            assertEquals(
                    authors.get(i).getWrittenBooks().size(),
                    dbAuthorsWithBooks.get(i).getWrittenBooks().size()
            );
        }
    }

    @Test
    @DisplayName("Save and flush author without books")
    void updateWithoutBooks() {
        val oldName = "Old Sanzhar";
        val newName = "New Sanzhar";
        var author = service.create(new Author(oldName, "Zhanibekov", new Date()));

        author.setName(newName);
        author = service.update(author);

        assertNotNull(author);
        Assertions.assertEquals(newName, author.getName());
    }

    @Test
    @DisplayName("Update author with books")
    void updateWithBooks() {
        val oldName = "Old old Sanzhar";
        val newName = "New new Sanzhar";
        var author = service.create(new Author(oldName, "Zhanibekov", new Date()));

        assertTrue(author.getWrittenBooks().isEmpty());

        var book = bookService.create(new Book(
                new BigDecimal(990),
                publisherService.create(new Publisher("Marvel")).getId(),
                "Iron Man", new Date()));

        author.setName(newName);
        author.setWrittenBooks(Set.of(book.getId()));

        author = service.update(author);

        assertNotNull(author);
        assertEquals(newName, author.getName());

        assertFalse(author.getWrittenBooks().isEmpty());
        assertEquals(1, author.getWrittenBooks().size());
    }

    @Test
    @DisplayName("Remove all authors without books ")
    void removeAllWithoutBooks() {

        service.create(new Author("Sanzhar", "Zhanibekov", new Date()));
        service.create(new Author("Another Sanzhar", "Zhanibekov", new Date()));
        service.create(new Author("Another another Sanzhar", "Zhanibekov", new Date()));

        assertEquals(3, service.read().size());

        bookService.read().stream()
                .map(Book::getId)
                .forEach(bookService::delete);

        assertEquals(0, service.read().size());
    }

    @Test
    @DisplayName("Remove all authors with books ")
    void removeAllWithBooks() {
        clean();
        val authors = List.of(
                new Author("Sanzhar", "Zhanibekov", new Date()),
                new Author("Another Sanzhar", "Zhanibekov", new Date()),
                new Author("Another another Sanzhar", "Zhanibekov", new Date()));

        val publisher = publisherService.create(new Publisher("Publisher 255"));

        val books = (Stream.of(
                new Book(new BigDecimal(1990), publisher.getId(), "title", new Date()),
                new Book(new BigDecimal(1990), publisher.getId(), "title", new Date()),
                new Book(new BigDecimal(1990), publisher.getId(), "title", new Date())
        )).map(book -> bookService.create(book)).toList();

        for (int i = 0; i < 3; i++) authors.get(i).setWrittenBooks(Set.of(books.get(i).getId()));

        val dbAuthors = authors.stream().map(author -> service.create(author)).toList();

        assertEquals(3, service.read().size());
        dbAuthors.forEach(author ->
                assertEquals(1, author.getWrittenBooks().size())); // assert books size each author


        bookService.read().stream()
                .map(Book::getId)
                .forEach(bookService::delete);

        assertEquals(0, service.read().size());
        assertEquals(0, bookService.read().size(),
                "Books can't removed, cause have problem with connection author and book." +
                        " When author save with books and removing, this connections can not to disconnect." +
                        " Check connection or need re-watch realizing logic remove in methods on AuthorEntity.class"); // assert removing in books
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