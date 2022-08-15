package kz.halykacademy.bookstore.serviceImpl;

import kz.halykacademy.bookstore.dto.Publisher;
import kz.halykacademy.bookstore.entity.BookEntity;
import kz.halykacademy.bookstore.entity.PublisherEntity;
import kz.halykacademy.bookstore.exceptions.businessExceptions.BusinessException;
import kz.halykacademy.bookstore.repository.BookRepository;
import kz.halykacademy.bookstore.repository.PublisherRepository;
import kz.halykacademy.bookstore.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PublisherServiceImpl extends BaseService<Publisher, PublisherEntity, PublisherRepository>
        implements PublisherService {

    private final BookRepository bookRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository repository, BookRepository bookRepository) {
        super(PublisherEntity.class, Publisher.class, repository);
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Publisher> findPublisherByName(String name) {
        var publisherList = repository.findAllByTitle(name);
        return publisherList.stream().map(this::convertToDto).toList();
    }

    @Override
    public Publisher create(Publisher publisher) throws BusinessException {
        PublisherEntity publisherEntity = convertToEntity(publisher);

        return save(publisherEntity);
    }

    @Override
    public List<Publisher> read() {
        return super.getAll();
    }

    @Override
    public Publisher read(Long id) throws BusinessException {
        return super.findById(id);
    }

    /**
     * @param publisher Dto to updating entity in database
     * @apiNote Universal algorithm that updating books in publishers
     */
    @Override
    public Publisher update(Publisher publisher) throws BusinessException {
        PublisherEntity publisherEntity;
        try {
            publisherEntity = convertToEntity(publisher);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }


//        if (publisher.getBooks() != null && publisherEntity.getBooks() != null) {
//            var currentIds = new ArrayList<>(publisherEntity.getBooks().stream().map(BookEntity::getId).toList());
//            var publisherIds = new ArrayList<>(publisherEntity.getBooks().stream().map(BookEntity::getId).toList());
//
//            if (publisher.getBooks().size() > publisherEntity.getBooks().size()) {
//                var resultId = ServiceUtils.uniqueIds(publisherIds, currentIds);
//                bookRepository.findAllById(resultId).forEach(publisherEntity::addBook);
//            } else if (publisher.getBooks().size() < publisherEntity.getBooks().size()) {
//                var resultId = ServiceUtils.uniqueIds(publisherIds, currentIds);
//                bookRepository.findAllById(resultId).forEach(publisherEntity::removeBook);
//            } else {
//                Collections.sort(currentIds);
//                Collections.sort(publisherIds);
//                for (int i = 0; i < currentIds.size(); i++) {
//                    if (!Objects.equals(currentIds.get(i), publisherIds.get(i))) {
//                        int y = i;
//                        publisherEntity.getBooks().stream()
//                                .filter(book -> publisherIds.get(y).equals(book.getId()))
//                                .findAny().ifPresent(publisherEntity::removeBook);
//                    }
//                }
//            }
//        }
        return saveAndFlush(publisherEntity);
    }

    @Override
    public void delete(Long id) throws BusinessException {
        var publisherEntity = repository.findById(id).orElse(null);
        if (publisherEntity == null) return;

        if (publisherEntity.getBooks() != null)
            publisherEntity.getBooks().forEach(publisherEntity::removeBook);
        saveAndFlush(publisherEntity); // updating data in database to remove all books from publisher

        removeById(id);
    }

    @Override
    protected Publisher convertToDto(PublisherEntity publisherEntity) {
        return Publisher.builder()
                .id(publisherEntity.getId())
                .title(publisherEntity.getTitle())
                .books(publisherEntity.getBooks().stream().map(BookEntity::getId).toList())
                .build();
    }

    @Override
    protected PublisherEntity convertToEntity(Publisher publisher) throws BusinessException {
        if (publisher == null) throw new BusinessException("Publisher can not be null", HttpStatus.NO_CONTENT);
        if (publisher.getBooks() == null) throw new BusinessException("Books in publisher can not be null", HttpStatus.BAD_REQUEST);

        return PublisherEntity.builder()
                .id(publisher.getId())
                .title(publisher.getTitle())
                .books(bookRepository.findAllById(publisher.getBooks()))
                .build();
    }
}
