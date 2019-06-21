package net.bakaar.sandbox.rest.mapper;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.rest.dto.PersonDTO;
import net.bakaar.sandbox.rest.dto.PersonalAddressDTO;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDtoMapperTest {


    @Test
    public void mapToDto_should_map_correctly() {
        //Given
        String id = "P12345678";
        PNumber pNumber = PNumber.of(id);
        String name = "Bloch";
        String forename = "Joshua";
        LocalDate birthDate = LocalDate.now();
        PersonDtoMapper mapper = new PersonDtoMapper();
        String addressLine = "23, rue du Bac";
        AddressNumber addressNumber = AddressNumber.of(7568463725L);
        PersonalAddress mainAddress = PersonalAddress.of(addressNumber, addressLine);
        Person inputPerson = Person.of(name, forename, birthDate, mainAddress).withId(pNumber).build();
        //When
        PersonDTO dto = mapper.mapToDto(inputPerson);
        //Then
        assertThat(dto).isNotNull();
        assertThat(dto.getForename()).isEqualTo(forename);
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getBirthDate()).isSameAs(birthDate);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getMainAddress()).isNotNull();
        PersonalAddressDTO mainAddressDto = dto.getMainAddress();
        assertThat(mainAddressDto.getAddress()).isEqualTo(addressLine);
        assertThat(mainAddressDto.getId()).isEqualTo(addressNumber.format());
    }
}
