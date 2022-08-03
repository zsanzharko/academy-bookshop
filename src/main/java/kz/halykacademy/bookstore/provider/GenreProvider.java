package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.entity.GenreEntity;
import kz.halykacademy.bookstore.repository.GenreRepository;
import kz.halykacademy.bookstore.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreProvider extends BaseProvider<Genre, GenreEntity, GenreRepository> implements GenreService {

    /**
     * @param repository   repository for provider
     */
    public GenreProvider(GenreRepository repository) {
        super(GenreEntity.class, Genre.class, repository);
    }

    @Override
    public Genre create(Genre entity) {
        return save(entity);
    }

    @Override
    public List<Genre> create(List<Genre> entities) {
        return saveAll(entities);
    }

    @Override
    public List<Genre> read() {
        return getAll();
    }

    @Override
    public Genre read(Long id) {
        return findById(id);
    }

    @Override
    public Genre update(Genre entity) {
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
        removeAll(ids);
    }
}
