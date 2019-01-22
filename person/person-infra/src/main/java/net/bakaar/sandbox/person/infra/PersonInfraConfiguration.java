package net.bakaar.sandbox.person.infra;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import net.bakaar.sandbox.person.infra.service.PersonRestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PersonInfraConfiguration {

    @Bean
    public CreatePartnerUseCase createPartnerApplicationService(
            @Qualifier("domainService") CreatePartnerUseCase partnerUseCase) {
        return new PersonRestService(partnerUseCase);
    }

    @Bean
    public CreatePartnerUseCase domainService(PartnerRepository store,
                                              BusinessNumberRepository businessNumberRepository) {
        return new PersonDomaineService(store, businessNumberRepository);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
