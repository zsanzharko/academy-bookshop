package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl extends BaseService<Author, AuthorEntity, AuthorRepository>
        implements AuthorService {

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, ModelMapper modelMapper) {
        super(AuthorEntity.class, Author.class, repository, modelMapper);
    }

    @Override
    public List<Author> findAuthorByFIO(String name, String surname, String patronymic) {
        var authorList = repository.findAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
        return getModelMap(authorList, Author.class);
    }

    @Override
    public Author create(Author author) {
        if (author == null) return null;
        var authorEntity = getModelMap(author, entityClass);

        return save(authorEntity);
    }

    @Override
    public List<Author> create(@NonNull List<Author> authors) {
        if (authors.isEmpty()) return null;
        var authorEntities = getModelMap(authors, entityClass);

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
        var authorEntity = getModelMap(author, entityClass);

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

    }
}
