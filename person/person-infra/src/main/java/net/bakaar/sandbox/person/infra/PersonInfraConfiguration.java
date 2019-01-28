package net.bakaar.sandbox.person.infra;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import net.bakaar.sandbox.person.infra.service.PersonApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonInfraConfiguration {

    @Bean
    public CreatePartnerUseCase createPartnerApplicationService(PartnerRepository partnerRepository,
                                                                BusinessNumberRepository numberRepository) {
        return new PersonApplicationService(new PersonDomaineService(partnerRepository, numberRepository));
    }
}
