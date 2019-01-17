package net.bakaar.sandbox.person.rest;

import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.controller.PartnerRestController;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.person.rest.service.PartnerReadService;
import net.bakaar.sandbox.person.rest.service.PersonRestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PartnerRestController.class)
public class PersonRestConfiguration {

    @Bean
    public CreatePartnerUseCase createPartnerApplicationService(
            @Qualifier("domainService") CreatePartnerUseCase partnerUseCase) {
        return new PersonRestService(partnerUseCase);
    }

    @Bean
    public PartnerReadStore readStoreApplicationService(@Qualifier("readStoreAdapter") PartnerReadStore readRepository) {
        return new PartnerReadService(readRepository);
    }

    @Bean
    public PartnerDomainDtoMapper partnerDomainDtoMapper() {
        return new PartnerDomainDtoMapper();
    }
}
