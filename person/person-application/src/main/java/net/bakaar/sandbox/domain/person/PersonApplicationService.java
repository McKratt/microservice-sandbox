package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

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
    public List<Person> searchPartners(SearchPartnerCommand command) {
        List<Person> people = new ArrayList<>();
        if (!isEmpty(command.getPNumber())) {
            people.add(personRepository.fetchPartnerById(PNumber.of(command.getPNumber())));
        }
        if (!isEmpty(command.getName()) || !isEmpty(command.getSocialNumber())) {
            SearchPersonQuery query = new SearchPersonQuery();
            query.setName(command.getName());
            query.setSocialSocialNumber(command.getSocialNumber());
            people.addAll(personRepository.searchPartner(query));
        }
        return people;
    }

    @Transactional(readOnly = true)
    public Person readPartner(PNumber id) {
        return personRepository.fetchPartnerById(id);
    }
}
