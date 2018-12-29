package net.bakaar.sandbox.person.rest.service;

import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;

import javax.transaction.Transactional;

public class PersonRestService {
    private final CreatePartnerUseCase createPartnerUseCase;
    private final PartnerDomainDtoMapper mapper;

    public PersonRestService(CreatePartnerUseCase createPartnerUseCase, PartnerDomainDtoMapper mapper) {
        this.createPartnerUseCase = createPartnerUseCase;
        this.mapper = mapper;
    }

    @Transactional
    public PartnerDTO createPartner(PartnerDTO partner) {
        return mapper.mapToDto(
                createPartnerUseCase.createPartner(partner.getName(), partner.getForename(), partner.getBirthDate())
        );
    }
}
