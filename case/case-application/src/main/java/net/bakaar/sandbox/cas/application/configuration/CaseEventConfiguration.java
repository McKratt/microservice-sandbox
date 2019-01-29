package net.bakaar.sandbox.cas.application.configuration;

import net.bakaar.sandbox.event.domain.EventStore;
import net.bakaar.sandbox.event.inmemory.InMemoryEventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseEventConfiguration {

    @Bean
    public EventStore eventStore() {
        return new InMemoryEventStore();
    }
}
