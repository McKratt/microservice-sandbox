package net.bakaar.sandbox.person.data.jpa.adapter;

import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.SearchPartnerQuery;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class PartnerRepositoryAdapter implements PartnerRepository {
    private final PersonJpaRepository repository;
    private final PartnerEntityDomainMapper entityDomainMapper;

    public PartnerRepositoryAdapter(PersonJpaRepository repository, PartnerEntityDomainMapper entityDomainMapper) {
        this.repository = repository;
        this.entityDomainMapper = entityDomainMapper;
    }

    @Override
    public Partner putPartner(Partner toStore) {
        return entityDomainMapper.mapToDomain(repository.save(entityDomainMapper.mapToEntity(toStore)));
    }

    @Override
    public Partner fetchPartnerById(PNumber number) {
        return entityDomainMapper.mapToDomain(repository.findByPNumber(number.getValue()));
    }

    @Override
    public List<Partner> searchPartner(SearchPartnerQuery query) {
        throw new NotImplementedException();
    }
}
