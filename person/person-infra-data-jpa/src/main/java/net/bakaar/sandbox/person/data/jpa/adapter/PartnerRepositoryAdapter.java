package net.bakaar.sandbox.person.data.jpa.adapter;

import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDTOMapper;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerRepositoryAdapter implements PartnerRepository, PartnerReadStore {
    private final PersonJpaRepository repository;
    private final PartnerEntityDomainMapper entityDomainMapper;
    private PartnerEntityDTOMapper entityDTOMapper;

    public PartnerRepositoryAdapter(PersonJpaRepository repository, PartnerEntityDomainMapper entityDomainMapper, PartnerEntityDTOMapper entityDTOMapper) {
        this.repository = repository;
        this.entityDomainMapper = entityDomainMapper;
        this.entityDTOMapper = entityDTOMapper;
    }

    @Override
    public Partner push(Partner toStore) {
        return entityDomainMapper.mapToDomain(repository.save(entityDomainMapper.mapToEntity(toStore)));
    }

    @Override
    public PartnerDTO fetchPartnerById(PNumber pNumber) {
        return entityDTOMapper.mapToDto(repository.findByPNumber(pNumber.getValue()));
    }
}
