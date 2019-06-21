package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;

public interface PersonRepository {

    Person putPerson(Person person);

    Person fetchPersonById(PNumber id);
}
