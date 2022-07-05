package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.provider.providable.Providable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseProvider<T extends Providable> implements Provider<T> {

    protected List<T> items;

    public BaseProvider() {
        this.items = new ArrayList<>();
        format();
    }

    public BaseProvider(List<T> items) {
        this.items = items;
        format();
    }

    public BaseProvider(T items) {
        this.items = List.of(items);
        format();
    }


    @Override
    public List<T> getItems() {
        return items;
    }

    @Override
    public List<T> getAll() {
        format();
        return items;
    }

    protected abstract void format();
}
