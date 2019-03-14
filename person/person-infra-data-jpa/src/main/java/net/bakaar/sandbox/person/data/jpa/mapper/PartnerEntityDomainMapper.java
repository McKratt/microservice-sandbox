package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerEntityDomainMapper {
    public Partner mapToDomain(PersonEntity entity) {
        return Partner.of(entity.getName(), entity.getForename(), entity.getBirthDate())
                .withId(PNumber.of(entity.getPNumber()))
                .build();
    }

    public PersonEntity mapToEntity(Partner partner) {
        PersonEntity entity = new PersonEntity();
        entity.setName(partner.getName().getLine());
        entity.setForename(partner.getForename().getLine());
        entity.setBirthDate(partner.getBirthDate());
        entity.setPNumber(partner.getId().getValue());
        return entity;
    }
}
