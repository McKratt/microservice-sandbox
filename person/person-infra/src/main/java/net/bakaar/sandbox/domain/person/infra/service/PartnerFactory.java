package net.bakaar.sandbox.domain.person.infra.service;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerFactory {
    private final BusinessNumberRepository businessNumberRepository;

    public PartnerFactory(BusinessNumberRepository businessNumberRepository) {
        this.businessNumberRepository = businessNumberRepository;
    }

    public Person createPartner(CreatePersonCommand command) {
        PNumber number = businessNumberRepository.fetchNextPNumber();
        return Person.of(command.getName(), command.getForename(), command.getBirthDate()).withId(number).build();
    }
}
