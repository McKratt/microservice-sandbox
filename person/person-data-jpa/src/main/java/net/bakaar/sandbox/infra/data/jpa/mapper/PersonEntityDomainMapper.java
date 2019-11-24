package net.bakaar.sandbox.infra.data.jpa.mapper;

import io.vavr.control.Option;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.person.SocialSecurityNumber;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.util.ArrayList;
import java.util.List;

public class PersonEntityDomainMapper {
    private final AddressEntityDomainMapper addressMapper;

    public PersonEntityDomainMapper(AddressEntityDomainMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Person mapToDomain(PersonEntity entity) {
        PersonalAddress mainAddress = null;
        List<PersonalAddress> secondariesAddresses = new ArrayList<>();
        for (PersonAddressesEntity link : entity.getPersonAddresses()) {
            if (link.isMain()) {
                mainAddress = addressMapper.mapToDomain(link.getAddress());
            } else {
                secondariesAddresses.add(addressMapper.mapToDomain(link.getAddress()));
            }
        }
        if (mainAddress == null) {
            throw new RuntimeException(String.format("The person : %s doesn't have any main address.", entity.getId()));
        }
        Person.BaseBuilder builder = Person.of(entity.getName(), entity.getForename(), entity.getBirthDate(), mainAddress)
                .withId(PNumber.of(entity.getNumber()));

        Option.of(entity.getSocialSecurityNumber())
                .peek((ssn) -> builder.withSocialSecurityNumber(SocialSecurityNumber.of(ssn)));
        return builder.build()
                .addSecondaryAddresses(secondariesAddresses.toArray(new PersonalAddress[secondariesAddresses.size()]));
    }

    public PersonEntity mapToEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setName(person.getName().getLine());
        entity.setForename(person.getForename().getLine());
        entity.setBirthDate(person.getBirthDate());
        entity.setNumber(person.getId().getValue());
        Option.of(person.getSocialSecurityNumber())
                .peek((ssn) -> entity.setSocialSecurityNumber(ssn.value()));
        PersonAddressesEntity mainLink = new PersonAddressesEntity();
        mainLink.setAddress(addressMapper.mapToEntity(person.getMainAddress()));
        mainLink.setPerson(entity);
        mainLink.setMain(true);
        entity.getPersonAddresses().add(mainLink);

        for (PersonalAddress current : person.getSecondaryAddresses()) {
            PersonAddressesEntity link = new PersonAddressesEntity();
            link.setAddress(addressMapper.mapToEntity(current));
            link.setPerson(entity);
            link.setMain(false);
            entity.getPersonAddresses().add(link);
        }
        return entity;
    }
}
