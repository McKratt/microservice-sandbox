package net.bakaar.sandbox.domain.person.data.jpa.mapper;

import net.bakaar.sandbox.domain.address.Address;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.domain.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerEntityDomainMapper {
    private final AddressEntityDomainMapper addressMapper;

    public PartnerEntityDomainMapper(AddressEntityDomainMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Person mapToDomain(PersonEntity entity) {
        Person.BaseBuilder builder = Person.of(entity.getName(), entity.getForename(), entity.getBirthDate())
                .withId(PNumber.of(entity.getPNumber()));
        if (entity.getSocialSecurityNumber() != 0) {
            builder.withSocialSecurityNumber(entity.getSocialSecurityNumber());
        }
        Person toReturn = builder.build();
        for (PersonAddressesEntity link : entity.getPersonAddresses()) {
            toReturn.addNewAddress(addressMapper.mapToDomain(link));
        }
        return toReturn;
    }

    public PersonEntity mapToEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setName(person.getName().getLine());
        entity.setForename(person.getForename().getLine());
        entity.setBirthDate(person.getBirthDate());
        entity.setPNumber(person.getId().getValue());
        entity.setSocialSecurityNumber(person.getSocialSecurityNumber());

        for (Address current : person.getSecondaryAddresses()) {
            PersonAddressesEntity link = new PersonAddressesEntity();
            link.setAddress(addressMapper.mapToEntity(current));
            link.setPerson(entity);
            link.setMain(current.isMain());
            entity.getPersonAddresses().add(link);
        }
        return entity;
    }
}
