package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.internal.PersonFactory;
import net.bakaar.sandbox.domain.person.internal.TransactionalPersonApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonApplicationConfiguration {

    @Bean
    public PersonApplicationService createPartnerApplicationService(PersonRepository personRepository,
                                                                    BusinessNumberRepository numberRepository) {
        return new TransactionalPersonApplicationService(personRepository, new PersonFactory(numberRepository));
    }
}
