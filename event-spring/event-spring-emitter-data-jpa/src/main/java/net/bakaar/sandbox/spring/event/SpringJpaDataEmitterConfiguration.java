package net.bakaar.sandbox.spring.event;

import net.bakaar.sandbox.event.MessageRaisedEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = MessageRaisedEntity.class)
@EnableJpaRepositories(basePackageClasses = MessageRaisedJpaRepository.class)
public class SpringJpaDataEmitterConfiguration {
}
