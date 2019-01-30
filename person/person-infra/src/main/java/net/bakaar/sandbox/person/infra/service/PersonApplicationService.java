package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.query.ReadPartnerQuery;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import org.springframework.transaction.annotation.Transactional;

public class PersonApplicationService implements CreatePartnerUseCase {
    private final CreatePartnerUseCase domainService;
    private final PartnerRepository partnerRepository;

    public PersonApplicationService(CreatePartnerUseCase domainService, PartnerRepository partnerRepository) {
        this.domainService = domainService;
        this.partnerRepository = partnerRepository;
    }

    @Transactional
    public Partner createPartner(CreatePartnerCommand command) {
        return domainService.createPartner(command);
    }

    @Transactional(readOnly = true)
    public Partner readPartner(ReadPartnerQuery query) {
        return partnerRepository.fetchPartner(query);
    }
}
