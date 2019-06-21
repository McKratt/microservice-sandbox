package net.bakaar.sandbox.domain.person.configuration;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonFactory;
import net.bakaar.sandbox.domain.person.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonApplicationConfiguration {

    @Bean
    public PersonApplicationService createPartnerApplicationService(PersonRepository personRepository,
                                                                    BusinessNumberRepository numberRepository) {
        return new PersonApplicationService(personRepository, new PersonFactory(numberRepository));
    }
}
