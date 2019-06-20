package net.bakaar.sandbox.domain.person.infra;

import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.infra.service.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.infra.service.PartnerFactory;
import net.bakaar.sandbox.domain.person.infra.service.PersonApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonInfraConfiguration {

    @Bean
    public PersonApplicationService createPartnerApplicationService(PersonRepository personRepository,
                                                                    BusinessNumberRepository numberRepository) {
        return new PersonApplicationService(personRepository, new PartnerFactory(numberRepository), numberRepository);
    }
}
