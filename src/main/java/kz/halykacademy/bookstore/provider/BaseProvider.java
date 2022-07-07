package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.entity.AbstractEntity;
import kz.halykacademy.bookstore.provider.providable.Providable;
import kz.halykacademy.bookstore.repository.CommonRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @param <P> Providable objects, need to access working with base provider
 * @author Sanzhar
 * @version 1.0
 * @apiNote Base Provider. Access all provider method and configurations.
 * @see Providable
 * @see Provider
 * @since 1.0
 */

public abstract class BaseProvider<
        P extends Providable,
        E extends AbstractEntity,
        R extends CommonRepository<E>> implements Provider<P> {

    private final Class<E> entityClass;
    private final Class<P> provideClass;
    protected final R repository;

    protected final List<E> items; // providable items

    public BaseProvider(@NonNull Class<E> entityClass,
                        @NonNull Class<P> provideClass,
                        @NonNull R repository) {
        this.entityClass = entityClass;
        this.provideClass = provideClass;
        this.items = repository.findAll();
        this.repository = repository;
    }

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Save entity to database.
     */
    @Override
    public P save(P entity) {
        return getModelMap(repository.save(getModelMap(entity, entityClass)),
                provideClass);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    @Override
    public List<P> saveAll(List<P> entities) {
        var model = repository.saveAll(getModelMap(entities, entityClass));
        return getModelMap(model, provideClass);
    }

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Update entity in database. Can work with JPA
     */
    @Override
    public P saveAndFlush(P entity) {
        var model = repository.saveAndFlush(getModelMap(entity, entityClass));
        return getModelMap(model, provideClass);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Update entities in database. Can work with JPA
     */
    @Override
    public List<P> saveAllAndFlush(List<P> entities) {
        var model = repository.saveAllAndFlush(getModelMap(entities, entityClass));
        return getModelMap(model, provideClass);
    }

    /**
     * @param entity Entity from database
     * @apiNote Removing entity
     */
    @Override
    public void remove(P entity) {
        repository.delete(getModelMap(entity, entityClass));
    }

    /**
     * @param entities List of entities
     * @apiNote Removing entities
     */
    @Override
    public void removeAll(List<P> entities) {
        repository.deleteAll(getModelMap(entities, entityClass));
    }

    /**
     * @apiNote Remove all entities in database
     */
    @Override
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    public P findById(Long id) {
        return getModelMap(Objects.requireNonNull(
                repository.findById(id).orElse(null)),
                provideClass);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<P> getAll() {
        format();
        return getModelMap(items, provideClass);
    }

    protected abstract void format();
}
