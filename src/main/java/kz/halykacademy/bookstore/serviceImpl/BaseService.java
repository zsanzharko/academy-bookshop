package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.entity.AbstractEntity;
import kz.halykacademy.bookstore.repository.CommonRepository;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.List;

/**
 * @param <P> DTOs objects, need to access working with base provider
 * @author Sanzhar
 * @version 1.0
 * @apiNote Base Provider. Access all provider method and configurations.
 * @see DTOs
 * @since 1.0
 */

public abstract class BaseService<
        P extends DTOs,
        E extends AbstractEntity,
        R extends CommonRepository<E>> {

    protected final Class<E> entityClass;
    protected final Class<P> dtoClass;

    @Getter
    protected final R repository;


    /**
     * @param entityClass Entity
     * @param dtoClass    DTO class
     * @param repository  repository for provider
     */
    public BaseService(@NonNull Class<E> entityClass,
                       @NonNull Class<P> dtoClass,
                       @NonNull R repository) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.repository = repository;
    }

    /**
     * @param e Entity from database
     * @return P DTO object
     * @apiNote Save entity to database.
     */
    protected P save(@NonNull E e) {
        return convertToDto(repository.save(e));
    }

    /**
     * @param es Entity from database
     * @return DTOs DTO object
     * @apiNote Save es to database.
     */
    protected List<P> saveAll(@NonNull List<E> es) {
        var models = repository.saveAll(es);
        return models.stream().map(this::convertToDto).toList();
    }

    /**
     * @param e Entity from database
     * @return DTOs DTO object
     * @apiNote Update entity in database. Can work with JPA
     */
    protected P saveAndFlush(@NonNull E e) {
        var model = repository.saveAndFlush(e);
        return convertToDto(model);
    }

    /**
     * @param es Entity from database
     * @apiNote Update entities in database. Can work with JPA
     */
    protected void saveAllAndFlush(@NonNull List<E> es) {
        repository.saveAllAndFlush(es);
    }

    /**
     * @param ids List of id entities
     * @apiNote Removing entities
     */
    protected void removeAll(@NonNull List<Long> ids) {
        var models = repository.findAllById(ids);
        models.forEach(m -> m.setRemoved(new Date(System.currentTimeMillis())));
        repository.saveAllAndFlush(models);
    }

    /**
     * @apiNote Remove all entities in database
     */
    protected void removeAll() {
        var models = repository.findAll();
        models.forEach(m -> {
            if (m.getRemoved() == null) m.setRemoved(new Date(System.currentTimeMillis()));
        });
        repository.saveAllAndFlush(models);
    }

    protected void removeById(@NonNull Long id) {
        var model = repository.findById(id).orElse(null);
        if (model == null) return;
        model.setRemoved(new Date(System.currentTimeMillis()));
        repository.saveAndFlush(model);
    }

    protected P findById(@NonNull Long id) {
        var model = repository.findById(id).orElse(null);
        if (model != null && model.getRemoved() == null) return convertToDto(model);
        return null;
    }

    protected List<P> getAll() {
        var models = repository.findAll();
        return models.stream()
                .filter(model-> model.getRemoved() == null)
                .map(this::convertToDto).toList();
    }

    protected abstract P convertToDto(E e);

    protected abstract E convertToEntity(P p);
}
