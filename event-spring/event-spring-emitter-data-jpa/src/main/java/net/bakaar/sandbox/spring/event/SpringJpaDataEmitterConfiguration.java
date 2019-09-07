package net.bakaar.sandbox.spring.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bakaar.sandbox.event.JpaEventEmitter;
import net.bakaar.sandbox.event.MessageProducer;
import net.bakaar.sandbox.event.MessageRaisedEntity;
import net.bakaar.sandbox.event.domain.DomainEmitter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = MessageRaisedEntity.class)
@EnableJpaRepositories(basePackageClasses = MessageRaisedJpaRepository.class)
public class SpringJpaDataEmitterConfiguration {

    @Bean
    DomainEmitter domainEmitter(MessageProducer messageProducer, MessageRaisedJpaRepository repository, ObjectMapper mapper) {
        return new JpaEventEmitter(messageProducer, new JsonMessageRaisedEntityMapper(mapper), new SpringDataMessageRaisedRepository(repository));
    }
}
