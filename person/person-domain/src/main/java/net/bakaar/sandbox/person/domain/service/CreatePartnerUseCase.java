package net.bakaar.sandbox.person.domain.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;

public interface CreatePartnerUseCase {
    Partner createPartner(CreatePartnerCommand command);
}
