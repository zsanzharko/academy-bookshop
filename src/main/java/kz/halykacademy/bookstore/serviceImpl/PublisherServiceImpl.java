package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl extends BaseService<Publisher, PublisherEntity, PublisherRepository>
        implements PublisherService {

    public PublisherServiceImpl(PublisherRepository repository) {
        super(PublisherEntity.class, Publisher.class, repository);
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        var publisherList = repository.findAllByTitle(name);
        return getModelMap(publisherList, Publisher.class);
    }

    @Override
    public Publisher create(Publisher publisher) {
        return save(publisher);
    }

    @Override
    public List<Publisher> create(List<Publisher> publishers) {
        return saveAll(publishers);
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
        return saveAndFlush(publisher);
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
