package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AuthorServiceImpl extends BaseService<Author, AuthorEntity, AuthorRepository>
        implements AuthorService {

    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, ModelMapper modelMapper, BookRepository bookRepository) {
        super(AuthorEntity.class, Author.class, repository, modelMapper);
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> findAuthorByFIO(String name, String surname, String patronymic) {
        var authorList = repository.findAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
        return authorList.stream().map(AuthorEntity::convert).toList();
    }

    @Override
    public Author create(Author author) {
        AuthorEntity authorEntity;
        try {
            authorEntity = getEntity(author);
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
            authorEntities = authors.stream()
                    .map(author ->
                            author.convert(new HashSet<>(
                                    bookRepository.findAllById(author.getWrittenBooks()))))
                    .toList();
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
            authorEntity = getEntity(author);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }

        return saveAndFlush(authorEntity);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public void deleteAll() {
        removeAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        // todo finish this method
    }

    private AuthorEntity getEntity(Author author) throws NullPointerException {
        if (author == null) throw new NullPointerException("Author can't be null");

        Set<BookEntity> bookEntities = new HashSet<>(bookRepository.findAllById(author.getWrittenBooks()));

        return author.convert(bookEntities);
    }

    @Override
    protected Author convertToDto(AuthorEntity authorEntity) {
        return authorEntity.convert();
    }

    @Override
    protected AuthorEntity convertToEntity(Author author) {
        return getEntity(author);
    }
}
