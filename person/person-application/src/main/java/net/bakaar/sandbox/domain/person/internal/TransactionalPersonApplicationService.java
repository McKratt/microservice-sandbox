package net.bakaar.sandbox.domain.person.internal;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalPersonApplicationService implements PersonApplicationService {
    private final PersonRepository personRepository;
    private final PersonFactory personFactory;

    public TransactionalPersonApplicationService(PersonRepository personRepository, PersonFactory factory) {
        this.personRepository = personRepository;
        this.personFactory = factory;
    }

    @Override
    @Transactional
    public Person createPerson(CreatePersonCommand command) {
        return personRepository.putPerson(personFactory.createPerson(command));
    }

    @Override
    @Transactional(readOnly = true)
    public Person readPerson(PNumber id) {
        return personRepository.fetchPersonById(id);
    }
}
