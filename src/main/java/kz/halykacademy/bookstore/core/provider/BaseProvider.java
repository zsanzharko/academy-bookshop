package kz.halykacademy.bookstore.core.provider;

import kz.halykacademy.bookstore.core.provider.providable.Providable;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> Providable objects, need to access working with base provider
 * @author Sanzhar
 * @version 1.0
 * @apiNote Base Provider. Access all provider method and configurations.
 * @see Providable
 * @see Provider
 * @since 1.0
 */
public abstract class BaseProvider<T extends Providable> implements Provider<T> {

    protected List<T> items; // providable items

    public BaseProvider(List<T> items) {
        if (items == null) items = new ArrayList<>();
        this.items = items;
        format();
    }

    public BaseProvider(T item) {
        if (item == null) this.items = new ArrayList<>();
        else this.items = List.of(item);
        format();
    }

    /**
     * @return formatted items
     */
    @Override
    public List<T> getAll() {
        format();
        return items;
    }

    protected abstract void format();
}
