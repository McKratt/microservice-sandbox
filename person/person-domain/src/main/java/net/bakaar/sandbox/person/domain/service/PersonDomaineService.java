package net.bakaar.sandbox.person.domain.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PersonDomaineService implements CreatePartnerUseCase {

    private final PartnerRepository store;
    private final BusinessNumberRepository businessNumberRepository;

    public PersonDomaineService(PartnerRepository store, BusinessNumberRepository businessNumberRepository) {
        this.store = store;
        this.businessNumberRepository = businessNumberRepository;
    }

    @Override
    public Partner createPartner(CreatePartnerCommand command) {
        PNumber id = businessNumberRepository.createPartnerNumber();
        return store.push(Partner.of(id, command.getName(), command.getForename(), command.getBirthDate()));
    }
}
