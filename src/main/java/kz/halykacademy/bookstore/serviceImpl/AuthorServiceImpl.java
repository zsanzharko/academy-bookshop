package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImpl extends BaseService<Author, AuthorEntity, AuthorRepository>
        implements AuthorService {

    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, BookRepository bookRepository) {
        super(AuthorEntity.class, Author.class, repository);
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> findAuthorByFIO(String name, String surname, String patronymic) {
        var authorList = repository.findAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
        return authorList.stream().map(this::convertToDto).toList();
    }

    @Override
    public Author create(Author author) {
        AuthorEntity authorEntity;
        try {
            authorEntity = convertToEntity(author);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }

        return save(authorEntity);
    }

    @Override
    public List<Author> create(@NonNull List<Author> authors) {
        List<AuthorEntity> authorEntities;
        try {
            authorEntities = authors.stream().map(this::convertToEntity).toList();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }

        return saveAll(authorEntities);
    }

    @Override
    public List<Author> read() {
        return super.getAll();
    }

    @Override
    public Author read(Long id) {
        return findById(id);
    }

    @Override
    public Author update(@NonNull Author author) {
        AuthorEntity authorEntity;
        try {
            authorEntity = convertToEntity(author);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }
        return saveAndFlush(authorEntity);
    }

    @Override
    public void delete(Long id) {
        AuthorEntity authorEntity = repository.findById(id).orElse(null);
        if (authorEntity == null) return;
        authorEntity.getWrittenBookList().forEach(bookEntity -> bookEntity.removeAuthor(authorEntity));
        super.removeById(id);
    }

    @Override
    public void deleteAll() {
        var models = repository.findAll();
        models.forEach(authorEntity -> {
            if (authorEntity.getWrittenBookList() != null || !authorEntity.getWrittenBookList().isEmpty()) {
                authorEntity.getWrittenBookList().forEach(bookEntity -> bookEntity.removeAuthor(authorEntity));
                bookRepository.saveAllAndFlush(authorEntity.getWrittenBookList());
            }
        });
        super.removeAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        repository.findAllById(ids).forEach(authorEntity -> {
            if (authorEntity.getWrittenBookList() != null) {
                authorEntity.getWrittenBookList().forEach(bookEntity -> bookEntity.removeAuthor(authorEntity));
                bookRepository.saveAllAndFlush(authorEntity.getWrittenBookList());
            }
        });
        super.removeAll(ids);
    }

    @Override
    protected Author convertToDto(AuthorEntity authorEntity) {
        return Author.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .surname(authorEntity.getSurname())
                .patronymic(authorEntity.getPatronymic())
                .birthday(authorEntity.getBirthday())
                .writtenBooks(authorEntity.getWrittenBookList().stream().map(BookEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    protected AuthorEntity convertToEntity(Author author) {
        if (author == null) throw new NullPointerException("Author can't be null");

        return AuthorEntity.builder()
                .id(author.getId())
                .name(author.getName())
                .surname((author.getSurname()))
                .patronymic(author.getPatronymic())
                .birthday(author.getBirthday())
                .writtenBookList(new HashSet<>(bookRepository.findAllById(author.getWrittenBooks())))
                .build();
    }

    private void updateData(final AuthorEntity author) {
        if (author == null || author.getWrittenBookList() == null) return;

        author.getWrittenBookList().forEach(bookEntity -> {
            author.removeBook(bookEntity);
            bookEntity.addAuthor(author);
            bookRepository.saveAndFlush(bookEntity);
            bookEntity.removeAuthor(author);
            author.addBook(bookEntity);
        });
    }
}
