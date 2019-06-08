package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerFactory {
    private final BusinessNumberRepository businessNumberRepository;

    public PartnerFactory(BusinessNumberRepository businessNumberRepository) {
        this.businessNumberRepository = businessNumberRepository;
    }

    public Partner createPartner(CreatePartnerCommand command) {
        PNumber number = businessNumberRepository.fetchNextPNumber();
        return Partner.of(command.getName(), command.getForename(), command.getBirthDate()).withId(number).build();
    }
}
