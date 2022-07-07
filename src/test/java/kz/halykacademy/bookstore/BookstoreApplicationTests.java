package kz.halykacademy.bookstore;

import kz.halykacademy.bookstore.dao.AuthorDao;
import kz.halykacademy.bookstore.dao.BookDao;
import kz.halykacademy.bookstore.dao.PublisherDao;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.dto.Book;
import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BookstoreApplicationTests {

    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private PublisherDao publisherDao;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(publisherRepository, "Repository did not autowired in test");
        Assertions.assertNotNull(authorRepository, "Repository did not autowired in test");
        Assertions.assertNotNull(bookRepository, "Repository did not autowired in test");

        Assertions.assertNotNull(bookDao, "Repository did not autowired in test");
        Assertions.assertNotNull(authorDao, "Repository did not autowired in test");
        Assertions.assertNotNull(publisherDao, "Repository did not autowired in test");
    }

    @Test
    @DisplayName("Save entities to db")
    public void saveEntitiesToDB() {
        Author author;
        Book book;
        Publisher publisher;

        author = new Author(1L, "Name", "Surname", "Patronymic", new Date(), null);

        book = new Book(1L, new BigDecimal(990), List.of(author), null, "Book title", 100, new Date());

        publisher = new Publisher(1L, "Publisher title", List.of(book));

        book.setPublisher(publisher);

        var dbPublisher = publisherDao.save(publisherDao.getModelMap(publisher, PublisherEntity.class));
        Assertions.assertNotNull(dbPublisher);
        Assertions.assertEquals(dbPublisher, publisher);
        Assertions.assertEquals(dbPublisher.getBookList(), List.of(book));

        var dbBook = bookDao.save(bookDao.getModelMap(book, BookEntity.class));
        Assertions.assertNotNull(dbBook);
        Assertions.assertEquals(dbBook, book);
        Assertions.assertEquals(dbBook.getAuthors(), List.of(author));

        var dbAuthor = authorDao.save(authorDao.getModelMap(author, AuthorEntity.class));
        Assertions.assertNotNull(dbAuthor);
        Assertions.assertEquals(dbAuthor, author);
    }

}
