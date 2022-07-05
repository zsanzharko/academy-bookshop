package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Author;

import java.util.List;

public class AuthorProvider extends BaseProvider<Author> {

    public AuthorProvider() {
        super();
    }

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
