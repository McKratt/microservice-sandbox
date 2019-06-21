package net.bakaar.sandbox.infra.data.jpa.adapter;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.infra.data.jpa.mapper.PersonEntityDomainMapper;
import net.bakaar.sandbox.infra.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PersonRepositoryAdapter implements PersonRepository {
    private final PersonJpaRepository repository;
    private final PersonEntityDomainMapper entityDomainMapper;

    public PersonRepositoryAdapter(PersonJpaRepository repository, PersonEntityDomainMapper entityDomainMapper) {
        this.repository = repository;
        this.entityDomainMapper = entityDomainMapper;
    }

    @Override
    public Person putPartner(Person toStore) {
        return entityDomainMapper.mapToDomain(repository.save(entityDomainMapper.mapToEntity(toStore)));
    }

    @Override
    public Person fetchPartnerById(PNumber number) {
        return entityDomainMapper.mapToDomain(repository.findByPNumber(number.getValue()));
    }
}
