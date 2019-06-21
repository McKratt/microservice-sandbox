package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;

public interface PersonRepository {

    Person putPartner(Person person);

    Person fetchPartnerById(PNumber id);
}
