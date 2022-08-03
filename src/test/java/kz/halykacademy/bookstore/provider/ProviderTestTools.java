package kz.halykacademy.bookstore.provider;

import kz.halykacademy.bookstore.entity.AbstractEntity;
import kz.halykacademy.bookstore.provider.providable.Providable;
import kz.halykacademy.bookstore.repository.CommonRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProviderTestTools {

    private static final String CLEAN_TAG = "CLEANER";

    public static void deleteAllEntities(List<BaseProvider<
            ? extends Providable,
            ? extends AbstractEntity,
            ? extends CommonRepository<? extends AbstractEntity>>> providers) {

        log.warn("Clean all entity in database...");
        try {
            for (var provider :
                    providers) {
                log.info(String.format("Provider: %s", provider.getClass().getName()));
                if (provider instanceof BookProvider p) {
                    p.deleteAll();
                } else if (provider instanceof AuthorProvider p) {
                    p.deleteAll();
                } else if (provider instanceof PublisherProvider p) {
                    p.deleteAll();
                }
            }

            log.info("Cleaning is done!");
        } catch (Exception e) {
            log.warn(CLEAN_TAG, e);
            log.warn("Can not clean database. Check database options");
        }
    }

    public static void deleteEntityById(Long id,
                                        BaseProvider<
                                                ? extends Providable,
                                                ? extends AbstractEntity,
                                                ? extends CommonRepository<? extends AbstractEntity>>
                                                provider) {
        log.info(String.format("Deleting database author (id=%s)...", id));
        try {

            if (provider instanceof BookProvider p) {
                p.delete(id);
            } else if (provider instanceof AuthorProvider p) {
                p.delete(id);
            } else if (provider instanceof PublisherProvider p) {
                p.delete(id);
            }

            log.info("Deleted");
        } catch (Exception e) {
            log.info(CLEAN_TAG, e);
            log.warn(String.format("Can not delete entity by id\nProvider: %s\nId entity: %s",
                    provider.getClass().getName(), id));
        }
    }
}
