package kz.halykacademy.bookstore.config;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@Configuration
public class DatabaseConfig {

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name = "Model Mapper")
    public ModelMapper modelMapper() {
        //fixme need to change null to empty collection
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection))
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//                .setPropertyCondition(Conditions.isNotNull())
//                .setMatchingStrategy(MatchingStrategies.LOOSE);
//                .setFieldMatchingEnabled(true)
//                .setAmbiguityIgnored(true);
        return modelMapper;
    }
}
