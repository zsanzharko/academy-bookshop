package kz.halykacademy.bookstore.core.provider;

import kz.halykacademy.bookstore.dto.Publisher;

import java.util.List;

public class PublisherProvider extends BaseProvider<Publisher>{

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
