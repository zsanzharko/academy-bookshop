package kz.halykacademy.bookstore.core.provider;

import kz.halykacademy.bookstore.dto.Author;

import java.util.List;

public class AuthorProvider extends BaseProvider<Author> {

    public AuthorProvider(Author author) {
        super(author);
    }

    public AuthorProvider(List<Author> authors) {
        super(authors);
    }

    @Override
    protected void format() {

    }
}
