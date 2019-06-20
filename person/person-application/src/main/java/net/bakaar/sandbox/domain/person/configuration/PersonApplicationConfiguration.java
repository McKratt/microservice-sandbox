package net.bakaar.sandbox.domain.person.configuration;

import net.bakaar.sandbox.domain.person.PartnerFactory;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.address.AddressRepository;
import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonApplicationConfiguration {

    @Bean
    public PersonApplicationService createPartnerApplicationService(PersonRepository personRepository,
                                                                    BusinessNumberRepository numberRepository,
                                                                    AddressRepository addressRepository) {
        return new PersonApplicationService(personRepository, new PartnerFactory(numberRepository, addressRepository), numberRepository);
    }
}
