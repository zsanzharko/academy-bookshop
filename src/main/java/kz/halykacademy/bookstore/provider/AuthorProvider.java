package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class AuthorProvider extends BaseProvider<Author, AuthorEntity, AuthorRepository> implements AuthorService {

    @Autowired
    public AuthorProvider(AuthorRepository repository) {
        super(AuthorEntity.class, Author.class, repository);
    }

    @Override
    public List<Author> findAuthorByFIO(String name, String surname, String patronymic) {
        var authorList = repository.findAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
        return getModelMap(authorList, Author.class);
    }

    @Override
    public Author create(Author entity) {
        if (entity == null) return null;

        final var bookProvider = ApplicationContextProvider.getApplicationContext().getBean(BookProvider.class);

        if (entity.getWrittenBooks() != null) {
            var book = bookProvider.create(entity.getWrittenBooks().stream().toList());
            entity.setWrittenBooks(new HashSet<>(book));
        }
        return save(entity);
    }

    @Override
    public List<Author> create(List<Author> entities) {
        if (entities == null || entities.isEmpty()) return null;

        final var bookProvider = ApplicationContextProvider.getApplicationContext().getBean(BookProvider.class);

        entities.forEach(entity -> {
            if (entity.getWrittenBooks() != null)
                bookProvider.create(entity.getWrittenBooks().stream().toList());
        });

        return saveAll(entities);
    }

    @Override
    public List<Author> read() {
        return getAll();
    }

    @Override
    public Author read(Long id) {
        return findById(id);
    }

    @Override
    public Author update(Author entity) {
        return saveAndFlush(entity);
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

    }

}
