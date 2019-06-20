package net.bakaar.sandbox.domain.person.rest.mapper;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.rest.dto.PartnerDTO;

public class PartnerDomainDtoMapper {
    public PartnerDTO mapToDto(Person person) {
        PartnerDTO dto = new PartnerDTO();
        dto.setId(person.getId().format());
        dto.setName(person.getName().getLine());
        dto.setForename(person.getForename().getLine());
        dto.setBirthDate(person.getBirthDate());
        return dto;
    }
}
