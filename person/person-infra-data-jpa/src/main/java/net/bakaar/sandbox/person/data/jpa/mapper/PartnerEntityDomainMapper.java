package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.domain.entity.Address;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerEntityDomainMapper {
    private final AddressEntityDomainMapper addressMapper;

    public PartnerEntityDomainMapper(AddressEntityDomainMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Partner mapToDomain(PersonEntity entity) {
        Partner.BaseBuilder builder = Partner.of(entity.getName(), entity.getForename(), entity.getBirthDate())
                .withId(PNumber.of(entity.getPNumber()));
        if (entity.getSocialSecurityNumber() != 0) {
            builder.withSocialSecurityNumber(entity.getSocialSecurityNumber());
        }
        Partner toReturn = builder.build();
        for (PersonAddressesEntity link : entity.getPersonAddresses()) {
            toReturn.addNewAddress(addressMapper.mapToDomain(link));
        }
        return toReturn;
    }

    public PersonEntity mapToEntity(Partner partner) {
        PersonEntity entity = new PersonEntity();
        entity.setName(partner.getName().getLine());
        entity.setForename(partner.getForename().getLine());
        entity.setBirthDate(partner.getBirthDate());
        entity.setPNumber(partner.getId().getValue());
        entity.setSocialSecurityNumber(partner.getSocialSecurityNumber());

        for (Address current : partner.getAddresses()) {
            PersonAddressesEntity link = new PersonAddressesEntity();
            link.setAddress(addressMapper.mapToEntity(current));
            link.setPerson(entity);
            link.setMain(current.isMain());
            entity.getPersonAddresses().add(link);
        }
        return entity;
    }
}
