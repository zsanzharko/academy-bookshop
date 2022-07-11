package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Author;
import kz.halykacademy.bookstore.entity.AuthorEntity;
import kz.halykacademy.bookstore.repository.AuthorRepository;
import kz.halykacademy.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorProvider extends BaseProvider<Author, AuthorEntity, AuthorRepository> implements AuthorService {

    @Autowired
    public AuthorProvider(AuthorRepository repository) {
        super(AuthorEntity.class, Author.class, repository);
    }
}
