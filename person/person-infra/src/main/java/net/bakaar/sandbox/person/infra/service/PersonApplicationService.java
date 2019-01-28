package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import org.springframework.transaction.annotation.Transactional;

public class PersonApplicationService implements CreatePartnerUseCase {
    private final CreatePartnerUseCase domainService;

    public PersonApplicationService(CreatePartnerUseCase domainService) {
        this.domainService = domainService;
    }

    @Transactional
    public Partner createPartner(CreatePartnerCommand command) {
        return domainService.createPartner(command);
    }
}
