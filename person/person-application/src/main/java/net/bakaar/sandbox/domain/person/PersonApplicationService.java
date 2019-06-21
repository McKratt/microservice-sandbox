package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

public class PersonApplicationService {
    private final PersonRepository personRepository;
    private final PartnerFactory partnerFactory;

    public PersonApplicationService(PersonRepository personRepository, PartnerFactory factory) {
        this.personRepository = personRepository;
        this.partnerFactory = factory;
    }

    @Transactional
    public Person createPartner(CreatePersonCommand command) {
        return personRepository.putPartner(partnerFactory.createPartner(command));
    }

    @Transactional(readOnly = true)
    public Person readPartner(PNumber id) {
        return personRepository.fetchPartnerById(id);
    }
}
