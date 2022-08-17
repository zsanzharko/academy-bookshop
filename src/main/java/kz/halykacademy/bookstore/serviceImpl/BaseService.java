package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.DTOs;
import kz.halykacademy.bookstore.entity.AbstractEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.repository.CommonRepository;
import lombok.Getter;
import org.springframework.http.HttpStatus;
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
     * @param e Entity from database
     * @return DTOs DTO object
     * @apiNote Update entity in database. Can work with JPA
     */
    protected P update(@NonNull E e) throws BusinessException {
        var model = repository.findById(e.getId()).orElseThrow(
                () -> new BusinessException("Entity with this id not found in database", HttpStatus.NOT_FOUND));
        if (model.getRemoved() != null)
            throw new BusinessException("This entity does not exist in the database", HttpStatus.NOT_FOUND);
        return convertToDto(repository.save(e));
    }

    protected void removeById(@NonNull Long id) throws BusinessException {
        var model = repository.findById(id).orElseThrow(() ->
                new BusinessException("Id is not find in database. Maybe it is already removed", HttpStatus.OK));
        if (model.getRemoved() != null) throw new BusinessException("Id is already removed", HttpStatus.OK);

        model.setRemoved(new Date(System.currentTimeMillis()));
        repository.saveAndFlush(model);
    }

    protected P findById(@NonNull Long id) throws BusinessException {
        var model = repository.findById(id).orElseThrow(() ->
                new BusinessException(String.format("Can't find entity by id (%s)", id), HttpStatus.NOT_FOUND));
        if (model.getRemoved() != null)
            throw new BusinessException(String.format("Can't find entity by id (%s)", id), HttpStatus.NOT_FOUND);

        return convertToDto(model);
    }

    protected List<P> getAll() {
        var models = repository.findAll();
        return models.stream()
                .filter(model -> model.getRemoved() == null)
                .map(this::convertToDto).toList();
    }

    protected abstract P convertToDto(E e);

    protected abstract E convertToEntity(P p) throws BusinessException;
}
