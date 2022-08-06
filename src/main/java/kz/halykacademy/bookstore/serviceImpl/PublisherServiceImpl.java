package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl extends BaseService<Publisher, PublisherEntity, PublisherRepository>
        implements PublisherService {

    public PublisherServiceImpl(PublisherRepository repository, ModelMapper modelMapper) {
        super(PublisherEntity.class, Publisher.class, repository, modelMapper);
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        var publisherList = repository.findAllByTitle(name);
        return getModelMap(publisherList, Publisher.class);
    }

    @Override
    public Publisher create(Publisher publisher) {
        var publisherEntity = getModelMap(publisher, entityClass);

        return save(publisherEntity);
    }

    @Override
    public List<Publisher> create(List<Publisher> publishers) {
        var publisherEntities = getModelMap(publishers, entityClass);

        return saveAll(publisherEntities);
    }

    @Override
    public List<Publisher> read() {
        return super.getAll();
    }

    @Override
    public Publisher read(Long id) {
        return super.findById(id);
    }

    @Override
    public Publisher update(Publisher publisher) {
        var publisherEntity = getModelMap(publisher, entityClass);

        return saveAndFlush(publisherEntity);
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
