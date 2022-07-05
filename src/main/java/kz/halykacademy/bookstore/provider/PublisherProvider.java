package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.dto.Publisher;

import java.util.List;

public class PublisherProvider extends BaseProvider<Publisher>{

    public PublisherProvider() {
        super();
    }

    public PublisherProvider(List<Publisher> items) {
        super(items);
    }

    public PublisherProvider(Publisher items) {
        super(items);
    }

    @Override
    protected void format() {

    }
}
