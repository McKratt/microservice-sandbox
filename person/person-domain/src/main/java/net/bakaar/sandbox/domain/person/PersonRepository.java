package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.util.List;

public interface PersonRepository {

    Person putPartner(Person person);

    Person fetchPartnerById(PNumber id);

    List<Person> searchPartner(SearchPersonQuery query);
}
