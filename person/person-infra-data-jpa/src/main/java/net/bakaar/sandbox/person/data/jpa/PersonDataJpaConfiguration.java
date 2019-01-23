package net.bakaar.sandbox.person.data.jpa;

import net.bakaar.sandbox.person.data.jpa.adapter.PartnerRepositoryAdapter;
import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDTOMapper;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = PersonJpaRepository.class)
@EntityScan(basePackageClasses = PersonEntity.class)
public class PersonDataJpaConfiguration {


    @Bean
    public PartnerRepository readStoreAdapter(PersonJpaRepository personJpaRepository) {
        return new PartnerRepositoryAdapter(personJpaRepository, new PartnerEntityDomainMapper(), new PartnerEntityDTOMapper());
    }
}
