package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

public class PersonApplicationService {
    private final PersonRepository personRepository;
    private final PersonFactory personFactory;

    public PersonApplicationService(PersonRepository personRepository, PersonFactory factory) {
        this.personRepository = personRepository;
        this.personFactory = factory;
    }

    @Transactional
    public Person createPerson(CreatePersonCommand command) {
        return personRepository.putPerson(personFactory.createPerson(command));
    }

    @Transactional(readOnly = true)
    public Person readPerson(PNumber id) {
        return personRepository.fetchPersonById(id);
    }
}
