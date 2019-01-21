package net.bakaar.sandbox.person.configuration;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonDomainConfiguration {

  @Bean
  public CreatePartnerUseCase domainService(PartnerRepository store, BusinessNumberRepository businessNumberRepository) {
    return new PersonDomaineService(store, businessNumberRepository);
  }


}
