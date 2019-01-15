package net.bakaar.sandbox.person.rest.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import javax.transaction.Transactional;

public class PersonRestService {
    private final CreatePartnerUseCase createPartnerUseCase;
    private final PartnerReadStore readRepository;
    private final PartnerDomainDtoMapper mapper;

    public PersonRestService(CreatePartnerUseCase createPartnerUseCase,
                             PartnerReadStore readRepository,
                             PartnerDomainDtoMapper mapper) {
        this.createPartnerUseCase = createPartnerUseCase;
        this.readRepository = readRepository;
        this.mapper = mapper;
    }

    @Transactional
    public PartnerDTO createPartner(PartnerDTO partner) {
        CreatePartnerCommand command =
                new CreatePartnerCommand(partner.getName(), partner.getForename(), partner.getBirthDate());
        return mapper.mapToDto(
                createPartnerUseCase.createPartner(command)
        );
    }

    // TODO put readOnly here for non blocking transaction
    @Transactional
    public PartnerDTO fetchPartnerById(PNumber pNumber) {
        return readRepository.fetchPartnerById(pNumber);
    }
}
