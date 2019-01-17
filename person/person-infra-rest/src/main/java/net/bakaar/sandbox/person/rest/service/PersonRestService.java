package net.bakaar.sandbox.person.rest.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

public class PersonRestService implements CreatePartnerUseCase {
    private final CreatePartnerUseCase createPartnerUseCase;

    public PersonRestService(@Qualifier("domainService") CreatePartnerUseCase createPartnerUseCase) {
        this.createPartnerUseCase = createPartnerUseCase;
    }

    @Transactional
    public Partner createPartner(CreatePartnerCommand command) {
        return createPartnerUseCase.createPartner(command);
    }
}
