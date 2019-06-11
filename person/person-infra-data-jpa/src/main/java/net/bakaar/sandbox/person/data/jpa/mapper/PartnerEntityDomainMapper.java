package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
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
        return builder.build();
    }

    public PersonEntity mapToEntity(Partner partner) {
        PersonEntity entity = new PersonEntity();
        entity.setName(partner.getName().getLine());
        entity.setForename(partner.getForename().getLine());
        entity.setBirthDate(partner.getBirthDate());
        entity.setPNumber(partner.getId().getValue());
        entity.setSocialSecurityNumber(partner.getSocialSecurityNumber());

//        for (Address current : partner.getAddresses()) {
//            entity.getAddresses().add(addressMapper.mapToEntity(current));
//        }
        return entity;
    }
}
