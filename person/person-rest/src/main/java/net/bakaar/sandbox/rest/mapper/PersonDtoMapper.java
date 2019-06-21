package net.bakaar.sandbox.rest.mapper;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.rest.dto.PersonDTO;
import net.bakaar.sandbox.rest.dto.PersonalAddressDTO;

public class PersonDtoMapper {
    public PersonDTO mapToDto(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId().format());
        dto.setName(person.getName().getLine());
        dto.setForename(person.getForename().getLine());
        dto.setBirthDate(person.getBirthDate());
        dto.setMainAddress(mapToAddressDto(person.getMainAddress()));
        return dto;
    }

    private PersonalAddressDTO mapToAddressDto(PersonalAddress address) {
        PersonalAddressDTO addressDto = new PersonalAddressDTO();
        addressDto.setId(address.getId().format());
        addressDto.setAddress(address.getAddress());
        return addressDto;
    }
}
