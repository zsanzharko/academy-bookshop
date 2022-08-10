package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Genre;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.GenreEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.GenreRepository;
import kz.halykacademy.bookstore.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GenreServiceImpl extends BaseService<Genre, GenreEntity, GenreRepository> implements GenreService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * @param repository       repository for provider
     * @param authorRepository repository for work with authors
     * @param bookRepository   repository for work with books
     */
    public GenreServiceImpl(GenreRepository repository, AuthorRepository authorRepository, BookRepository bookRepository) {
        super(GenreEntity.class, Genre.class, repository);
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Genre create(Genre genre) {
        GenreEntity genreEntity;
        try {
            genreEntity = convertToEntity(genre);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }
        return save(genreEntity);
    }

    @Override
    public List<Genre> create(List<Genre> genres) {
        List<GenreEntity> genreEntities;
        try {
            genreEntities = genres.stream().map(this::convertToEntity).toList();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }

        return saveAll(genreEntities);
    }

    @Override
    public List<Genre> read() {
        return super.getAll();
    }

    @Override
    public Genre read(Long id) {
        return super.findById(id);
    }

    @Override
    public Genre update(Genre genre) {
        GenreEntity genreEntity;
        try {
            genreEntity = convertToEntity(genre);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }

        return super.saveAndFlush(genreEntity);
    }

    @Override
    public void delete(Long id) {
        var genreEntity = repository.findById(id);
        genreEntity.ifPresent(genre ->
                genre.getBooks().forEach(book ->
                        book.getGenres().forEach(book::removeGenre)));
        genreEntity.ifPresent(this::saveAndFlush);
        super.removeById(id);
    }

    @Override
    public void deleteAll() {
        var genreEntities = repository.findAll();
        genreEntities.forEach(genre ->
                genre.getBooks().forEach(book ->
                        book.getGenres().forEach(book::removeGenre)));
        saveAllAndFlush(genreEntities);
        super.removeAll();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        var genreEntities = repository.findAllById(ids);
        genreEntities.forEach(genre ->
                genre.getBooks().forEach(book ->
                        book.getGenres().forEach(book::removeGenre)));
        saveAllAndFlush(genreEntities);
        super.removeAll();
    }

    @Override
    protected Genre convertToDto(GenreEntity genreEntity) {
        if (genreEntity == null) return null;
        var authors = genreEntity.getAuthors().stream().map(AuthorEntity::getId).collect(Collectors.toSet());
        var books = genreEntity.getBooks().stream().map(BookEntity::getId).collect(Collectors.toSet());

        return Genre.builder()
                .id(genreEntity.getId())
                .title(genreEntity.getTitle())
                .authors(authors)
                .books(books)
                .build();
    }

    @Override
    protected GenreEntity convertToEntity(Genre genre) {
        if (genre == null) throw new NullPointerException("Genre can not be null");
        if (genre.getBooks() == null || genre.getAuthors() == null)
            throw new NullPointerException("Books or Authors in genre can not be null. Cause Authors and Books is merged");

        return GenreEntity.builder()
                .id(genre.getId())
                .title(genre.getTitle())
                .authors(new HashSet<>(authorRepository.findAllById(genre.getAuthors())))
                .books(new HashSet<>(bookRepository.findAllById(genre.getBooks())))
                .build();
    }
}
