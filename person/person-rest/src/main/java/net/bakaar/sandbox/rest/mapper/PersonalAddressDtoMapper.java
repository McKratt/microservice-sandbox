package net.bakaar.sandbox.rest.mapper;

import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.rest.dto.PersonalAddressDTO;

public class PersonalAddressDtoMapper {
    public PersonalAddress mapToDomain(PersonalAddressDTO dto) {

        return PersonalAddress.of(AddressNumber.of(dto.getId()), dto.getAddress());
    }
}
