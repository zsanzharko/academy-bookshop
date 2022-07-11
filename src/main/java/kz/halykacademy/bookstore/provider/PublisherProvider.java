package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import org.springframework.stereotype.Service;

@Service
public class PublisherProvider extends BaseProvider<Publisher, PublisherEntity, PublisherRepository>
        implements PublisherService {

    public PublisherProvider(PublisherRepository repository) {
        super(PublisherEntity.class, Publisher.class, repository);
    }
}
