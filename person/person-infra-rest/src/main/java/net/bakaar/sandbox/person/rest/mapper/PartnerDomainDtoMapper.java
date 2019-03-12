package net.bakaar.sandbox.person.rest.mapper;

import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;

public class PartnerDomainDtoMapper {
    public PartnerDTO mapToDto(Partner partner) {
        PartnerDTO dto = new PartnerDTO();
        dto.setId(partner.getId().format());
        dto.setName(partner.getName().getLine());
        dto.setForename(partner.getForename().getLine());
        dto.setBirthDate(partner.getBirthDate());
        return dto;
    }
}
