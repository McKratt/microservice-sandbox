package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;

public class PartnerEntityDTOMapper {
    public PartnerDTO mapToDto(PersonEntity entity) {
        PartnerDTO dto = new PartnerDTO();
        dto.setForename(entity.getForename());
        dto.setName(entity.getName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setId("P" + entity.getPNumber());
        return dto;
    }
}
