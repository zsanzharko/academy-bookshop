package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.entity.AbstractEntity;
import kz.halykacademy.bookstore.entity.Entitiable;
import kz.halykacademy.bookstore.provider.providable.AccountProvidable;
import kz.halykacademy.bookstore.provider.providable.Providable;
import kz.halykacademy.bookstore.provider.providable.ShopProvidable;
import kz.halykacademy.bookstore.repository.CommonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <P> Providable objects, need to access working with base provider
 * @author Sanzhar
 * @version 1.0
 * @apiNote Base Provider. Access all provider method and configurations.
 * @see Providable
 * @since 1.0
 */

public abstract class BaseProvider<
        P extends Providable,
        E extends AbstractEntity,
        R extends CommonRepository<E>> {

    private final Class<E> entityClass;
    private final Class<P> provideClass;
    protected final R repository;

    /**
     * @param entityClass Entity
     * @param provideClass Provide class
     * @param repository repository for provider
     */
    public BaseProvider(@NonNull Class<E> entityClass,
                        @NonNull Class<P> provideClass,
                        @NonNull R repository) {
        this.entityClass = entityClass;
        this.provideClass = provideClass;
        this.repository = repository;
    }

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Save entity to database.
     */
    protected P save(@NonNull P entity) {
        return getModelMap(repository.save(getModelMap(entity, entityClass)),
                provideClass);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    protected List<P> saveAll(@NonNull List<P> entities) {
        var model = repository.saveAll(getModelMap(entities, entityClass));
        return getModelMap(model, provideClass);
    }

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Update entity in database. Can work with JPA
     */
    protected P saveAndFlush(@NonNull P entity) {
        var model = repository.saveAndFlush(getModelMap(entity, entityClass));
        return getModelMap(model, provideClass);
    }

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Update entities in database. Can work with JPA
     */
    protected List<P> saveAllAndFlush(@NonNull List<P> entities) {
        var model = repository.saveAllAndFlush(getModelMap(entities, entityClass));
        return getModelMap(model, provideClass);
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
        models.forEach(m -> m.setRemoved(new Date(System.currentTimeMillis())));
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
        if (model != null && model.getRemoved() == null) return getModelMap(model, provideClass);
        return null;
    }

    protected List<P> getAll() {
        var items = repository.findAll();
        return getModelMap(items, provideClass);
    }

    /**
     * @param source          object, who need to map
     * @param destinationType type to mapping
     * @param <D>             Type to checking to accessible converting to object
     * @return Destination type object
     * @apiNote Map source object to destination type. Need to convert entity to DTO.
     * It will check source and destination to interfaces
     * @see Providable
     * @see Entitiable
     * @see ModelMapper
     * @see ApplicationContextProvider
     */
    protected  <D> D getModelMap(@NonNull Object source, Class<D> destinationType) {
        // todo add exception
        var modelMapper = ApplicationContextProvider.getApplicationContext().getBean(ModelMapper.class);
        boolean desCor = false;
        boolean sourCor = false;
        for (var s : source.getClass().getInterfaces()) {
            if (s.getName().equals(Providable.class.getName()) ||
                    s.getName().equals(Entitiable.class.getName()) ||
                    s.getName().equals(ShopProvidable.class.getName()) ||
                    s.getName().equals(AccountProvidable.class.getName())
            ) {
                sourCor = true;

                break;
            }
        }
        for (var d : destinationType.getInterfaces()) {
            if (d.getName().equals(Providable.class.getName()) ||
                    d.getName().equals(Entitiable.class.getName()) ||
                    d.getName().equals(ShopProvidable.class.getName()) ||
                    d.getName().equals(AccountProvidable.class.getName())
            ) {
                desCor = true;

                break;
            }
        }

        if (desCor && sourCor) return modelMapper.map(source, destinationType);

        return null;
    }

    /**
     * @param sources         List objects, who need to map
     * @param destinationType type to mapping
     * @param <D>             Type to checking to accessible converting to object
     * @return Destination type list objects
     * @apiNote Map source objects to destination type. Need to convert entities to DTO's.
     * It will check source and destination classes to interfaces
     * @see Providable
     * @see Entitiable
     * @see ModelMapper
     * @see ApplicationContextProvider
     */
    protected <D> List<D> getModelMap(@NonNull List<?> sources, Class<D> destinationType) {
        // todo add exception
        List<D> mappers = new ArrayList<>(sources.size());
        for (var s : sources) {
            mappers.add(getModelMap(s, destinationType));
        }
        return mappers;
    }
}
