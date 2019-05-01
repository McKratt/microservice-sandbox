package net.bakaar.sandbox.person.infra;

import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.infra.service.BusinessNumberRepository;
import net.bakaar.sandbox.person.infra.service.PartnerFactory;
import net.bakaar.sandbox.person.infra.service.PersonApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonInfraConfiguration {

    @Bean
    public PersonApplicationService createPartnerApplicationService(PartnerRepository partnerRepository,
                                                                    BusinessNumberRepository numberRepository) {
        return new PersonApplicationService(partnerRepository, new PartnerFactory(numberRepository), numberRepository);
    }
}
