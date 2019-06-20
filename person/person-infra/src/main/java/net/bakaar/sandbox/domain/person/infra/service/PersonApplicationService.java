package net.bakaar.sandbox.domain.person.infra.service;

import net.bakaar.sandbox.domain.address.Address;
import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.SearchPersonQuery;
import net.bakaar.sandbox.domain.person.infra.service.vo.CreateAddressCommand;
import net.bakaar.sandbox.domain.person.infra.service.vo.SearchPartnerCommand;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PersonApplicationService {
    private final PersonRepository personRepository;
    private final PartnerFactory partnerFactory;
    private final BusinessNumberRepository idRepository;

    public PersonApplicationService(PersonRepository personRepository, PartnerFactory factory, BusinessNumberRepository idRepository) {
        this.personRepository = personRepository;
        this.partnerFactory = factory;
        this.idRepository = idRepository;
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

    @Transactional
    public Person addAddressToPartner(PNumber partnerId, CreateAddressCommand command) {
        Person person = personRepository.fetchPartnerById(partnerId);
        if (person == null) {
            throw new IllegalArgumentException(String.format("The person id %s doesn't exist", partnerId.format()));
        }
        // TODO move this part inside the factory
        AddressNumber addressId = idRepository.fetchNextAddressNumber();
        Address newAddress = Address.of(addressId, command.getAddress());

        person.addNewAddress(newAddress);
        return personRepository.putPartner(person);
    }
}
