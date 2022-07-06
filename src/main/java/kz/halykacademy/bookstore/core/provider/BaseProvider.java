package kz.halykacademy.bookstore.core.provider;

import kz.halykacademy.bookstore.core.provider.providable.Providable;

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
    public List<T> getAll() {
        format();
        return items;
    }

    protected abstract void format();
}
