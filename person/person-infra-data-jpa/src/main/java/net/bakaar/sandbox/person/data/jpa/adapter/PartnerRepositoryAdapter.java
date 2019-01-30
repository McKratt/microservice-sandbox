package net.bakaar.sandbox.person.data.jpa.adapter;

import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.query.ReadPartnerQuery;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;

public class PartnerRepositoryAdapter implements PartnerRepository {
    private final PersonJpaRepository repository;
    private final PartnerEntityDomainMapper entityDomainMapper;

    public PartnerRepositoryAdapter(PersonJpaRepository repository, PartnerEntityDomainMapper entityDomainMapper) {
        this.repository = repository;
        this.entityDomainMapper = entityDomainMapper;
    }

    @Override
    public Partner push(Partner toStore) {
        return entityDomainMapper.mapToDomain(repository.save(entityDomainMapper.mapToEntity(toStore)));
    }

    @Override
    public Partner fetchPartner(ReadPartnerQuery query) {
        return entityDomainMapper.mapToDomain(repository.findByPNumber(query.getNumberOfPartnerToFound().getValue()));
    }
}
