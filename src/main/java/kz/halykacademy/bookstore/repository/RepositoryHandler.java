package kz.halykacademy.bookstore.repository;


import kz.halykacademy.bookstore.config.ApplicationContextProvider;
import kz.halykacademy.bookstore.core.provider.providable.Providable;
import kz.halykacademy.bookstore.entity.Entitiable;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote Main repository handler that can to implement entity to model or reverse.
 * @author Sanzhar
 * @version 1.0
 * @param <E> Entity
 * @param <P> Provider model
 */
public interface RepositoryHandler<
        E extends Entitiable,
        P extends Providable> {

    P save(E entity);
    List<P> saveAll(List<E> entities);

    /**
     * @param source object, who need to map
     * @param destinationType type to mapping
     * @param <D> Type to checking to accessible converting to object
     * @return Destination type object
     */
    default <D> D getModelMap(Object source, Class<D> destinationType) {
        // todo add exception
        var modelMapper = ApplicationContextProvider.getApplicationContext().getBean(ModelMapper.class);
        boolean desCor = false;
        boolean sourCor = false;

        for(var d : destinationType.getInterfaces()) {
            if (d.getName().equals(Providable.class.getName()) ||
                    d.getName().equals(Entitiable.class.getName())) {
                desCor = true;

                break;
            }
        }

        for (var s : source.getClass().getInterfaces()) {
            if (s.getInterfaces().getClass().getName().equals(Providable.class.getName()) ||
                    s.getInterfaces().getClass().getName().equals(Entitiable.class.getName())) {
                sourCor = true;

                break;
            }
        }

        if (desCor && sourCor) {
            return modelMapper.map(source, destinationType);
        }

        return null;
    }

    default <D> List<D> getModelMap(List<?> sources, Class<D> destinationType) {
        // todo add exception
        List<D> mappers = new ArrayList<>(sources.size());
        for (var s : sources) {
            getModelMap(s, destinationType);
        }
        return mappers;
    }

    P saveAndFlush(E entity);
    List<P> saveAllAndFlush(List<E> entities);

    void remove(E entity);

    void removeAll(List<E> entities);

    void removeAll();
}
