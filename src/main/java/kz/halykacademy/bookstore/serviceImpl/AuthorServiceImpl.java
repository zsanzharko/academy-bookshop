package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.GenreRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImpl extends BaseService<Author, AuthorEntity, AuthorRepository>
        implements AuthorService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, BookRepository bookRepository, GenreRepository genreRepository) {
        super(repository);
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Author> findAuthorByFIO(String name, String surname, String patronymic) {
        var authorList = repository.findAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
        return authorList.stream()
                .filter(author -> author.getRemoved() == null)
                .map(this::convertToDto).toList();
    }

    @Override
    public List<Author> findAuthorsByGenres(List<String> genresName) throws BusinessException {
        if (genresName.size() == 0) throw new BusinessException("Invalid input on genres name", HttpStatus.BAD_REQUEST);
        var genresList = genresName.stream()
                .map(genreRepository::findAllByTitle)
                .toList();

        Set<Author> authors = new HashSet<>();

        genresList.forEach(genres -> genres.stream()
                .filter(genreEntity -> genreEntity.getRemoved() == null)
                .forEach(genre -> genre.getBooks().stream()
                .map(BookEntity::getAuthors)
                .forEach(authorEntities -> authors.addAll(authorEntities.stream()
                        .map(this::convertToDto).collect(Collectors.toSet())))));

        return authors.stream().toList();
    }

    @Override
    public Author create(Author author) throws BusinessException, NullPointerException {
        AuthorEntity authorEntity = convertToEntity(author);
        return save(authorEntity);
    }

    @Override
    public List<Author> read() {
        return super.getAll();
    }

    @Override
    public Author read(Long id) throws BusinessException {
        return findById(id);
    }

    @Override
    public Author update(@NonNull Author author) throws BusinessException, NullPointerException {
        if (author.getId() == null)
            throw new BusinessException("Id to update author must not be null", HttpStatus.BAD_REQUEST);

        AuthorEntity authorEntity = convertToEntity(author);
        return update(authorEntity);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        AuthorEntity authorEntity = repository.findById(id).orElse(null);
        if (authorEntity == null) return;
        authorEntity.getWrittenBookList().forEach(bookEntity -> bookEntity.removeAuthor(authorEntity));
        super.removeById(id);
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
    protected AuthorEntity convertToEntity(Author author) throws BusinessException, NullPointerException {
        if (author == null) throw new NullPointerException("Author can't be null");
        if (author.getWrittenBooks() == null)
            throw new BusinessException("Books in authors can not be null", HttpStatus.BAD_REQUEST);

        return AuthorEntity.builder()
                .id(author.getId())
                .name(author.getName())
                .surname((author.getSurname()))
                .patronymic(author.getPatronymic())
                .birthday(author.getBirthday())
                .writtenBookList(author.getWrittenBooks() == null ? new HashSet<>() :
                        new HashSet<>(bookRepository.findAllById(author.getWrittenBooks())))
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
