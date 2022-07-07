package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.entity.Entitiable;
import kz.halykacademy.bookstore.provider.providable.AccountProvidable;
import kz.halykacademy.bookstore.provider.providable.Providable;
import kz.halykacademy.bookstore.provider.providable.ShopProvidable;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanzhar
 * @version 0.2
 * @apiNote Main service that can to implement entity to model or reverse.
 * @since 0.2
 */
public interface Provider<T extends Providable> {

    /**
     * @param entity Entity from database
     * @return Providable DTO object
     * @apiNote Save entity to database.
     */
    T save(T entity);

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Save entities to database.
     */
    List<T> saveAll(List<T> entities);

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
    T saveAndFlush(T entity);

    /**
     * @param entities Entity from database
     * @return Providable DTO object
     * @apiNote Update entities in database. Can work with JPA
     */
    List<T> saveAllAndFlush(List<T> entities);

    /**
     * @param entity Entity from database
     * @apiNote Removing entity
     */
    void remove(T entity);

    /**
     * @param entities List of entities
     * @apiNote Removing entities
     */
    void removeAll(List<T> entities);

    /**
     * @apiNote Remove all entities in database
     */
    void removeAll();

    /**
     * @param id Long-identifier in database
     * @return DTO pbject
     */
    T findById(Long id);

    /**
     * @param id Long-Identification in entity
     */
    void removeById(Long id);

    List<T> getAll();
}
