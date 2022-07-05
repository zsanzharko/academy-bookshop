package kz.halykacademy.bookstore.provider;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseProvider<T> implements Provider<T> {

    @Getter
    protected List<T> items;

    public BaseProvider() {
        items = new ArrayList<>();
        format();
    }

    @Override
    public List<T> getAll() {
        format();
        return items;
    }

    protected abstract void format();
}
