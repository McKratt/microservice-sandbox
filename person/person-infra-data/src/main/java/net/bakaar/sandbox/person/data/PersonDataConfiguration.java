package net.bakaar.sandbox.person.data;

import net.bakaar.sandbox.person.data.jpa.adapter.PartnerRepositoryAdapter;
import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDTOMapper;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.data.rest.BusinessNumberRepositoryAdapter;
import net.bakaar.sandbox.person.data.rest.BusinessNumberServiceProperties;
import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(BusinessNumberServiceProperties.class)
@EnableJpaRepositories(basePackageClasses = PersonJpaRepository.class)
@EntityScan(basePackageClasses = PersonEntity.class)
public class PersonDataConfiguration {

    @Bean
    public BusinessNumberRepository businessNumberService(BusinessNumberServiceProperties properties, RestTemplate restTemplate) {
        return new BusinessNumberRepositoryAdapter(properties, restTemplate);
    }

    @Bean
    public PartnerRepository readStoreAdapter(PersonJpaRepository personJpaRepository) {
        return new PartnerRepositoryAdapter(personJpaRepository, new PartnerEntityDomainMapper(), new PartnerEntityDTOMapper());
    }
}
