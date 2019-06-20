package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerFactory {
    private final BusinessNumberRepository businessNumberRepository;


    public PartnerFactory(BusinessNumberRepository businessNumberRepository) {
        this.businessNumberRepository = businessNumberRepository;
    }

    Person createPartner(CreatePersonCommand command) {
        PNumber number = businessNumberRepository.fetchNextPNumber();
        return Person
                .of(command.getName(), command.getForename(), command.getBirthDate(), command.getMainAddress())
                .withId(number)
                .build();
    }
}
