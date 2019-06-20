package net.bakaar.sandbox.infra.data.jpa;

import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.infra.data.jpa.adapter.PersonRepositoryAdapter;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.infra.data.jpa.mapper.AddressEntityDomainMapper;
import net.bakaar.sandbox.infra.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.infra.data.jpa.repository.AddressJpaRepository;
import net.bakaar.sandbox.infra.data.jpa.repository.PersonJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = PersonJpaRepository.class)
@EntityScan(basePackageClasses = PersonEntity.class)
public class PersonDataJpaConfiguration {

    @Bean
    public PersonRepository readStoreAdapter(PersonJpaRepository personJpaRepository, AddressJpaRepository addressJpaRepository) {
        return new PersonRepositoryAdapter(personJpaRepository, new PartnerEntityDomainMapper(new AddressEntityDomainMapper(addressJpaRepository)));
    }
}
