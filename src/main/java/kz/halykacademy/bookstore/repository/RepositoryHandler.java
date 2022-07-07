package kz.halykacademy.bookstore.repository;


import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.core.provider.providable.Providable;
import kz.halykacademy.bookstore.entity.Entitiable;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <E> Entity
 * @param <P> Provider model
 * @author Sanzhar
 * @version 1.1
 * @apiNote Main repository handler that can to implement entity to model or reverse.
 */
public interface RepositoryHandler<
        E extends Entitiable,
        P extends Providable> {

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Save entity to database.
     */
    P save(E entity);

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    List<P> saveAll(List<E> entities);

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
    default <D> D getModelMap(Object source, Class<D> destinationType) {
        // todo add exception
        var modelMapper = ApplicationContextProvider.getApplicationContext().getBean(ModelMapper.class);
        boolean desCor = false;
        boolean sourCor = false;

        var i =  source.getClass().getInterfaces();


        for (var d : destinationType.getInterfaces()) {
            if (d.getName().equals(Providable.class.getName()) ||
                    d.getName().equals(Entitiable.class.getName())) {
                desCor = true;

                break;
            }
        }

        for (var s : source.getClass().getInterfaces()) {
            if (s.getName().equals(Providable.class.getName()) ||
                    s.getName().equals(Entitiable.class.getName())) {
                sourCor = true;

                break;
            }
        }

        if (desCor && sourCor) {
            return modelMapper.map(source, destinationType);
        }

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
    default <D> List<D> getModelMap(List<?> sources, Class<D> destinationType) {
        // todo add exception
        List<D> mappers = new ArrayList<>(sources.size());
        for (var s : sources) {
            getModelMap(s, destinationType);
        }
        return mappers;
    }


    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Update entity in database. Can work with JPA
     */
    P saveAndFlush(E entity);

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Update entities in database. Can work with JPA
     */
    List<P> saveAllAndFlush(List<E> entities);

    /**
     * @param entity Entity from database
     * @apiNote Removing entity
     */
    void remove(E entity);

    /**
     * @param entities List of entities
     * @apiNote Removing entities
     */
    void removeAll(List<E> entities);

    /**
     * @apiNote Remove all entities in database
     */
    void removeAll();
}
