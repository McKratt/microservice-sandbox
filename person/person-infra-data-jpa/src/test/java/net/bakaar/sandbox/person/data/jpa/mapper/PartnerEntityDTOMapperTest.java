package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerEntityDTOMapperTest {

    @Test
    public void mapToDto_should_map_correctly() {
        //Given
        PersonEntity entity = new PersonEntity();
        entity.setId(98765432L);
        long id = 12345678L;
        entity.setPNumber(id);
        String name = "Einstein";
        entity.setName(name);
        String forename = "Albert";
        entity.setForename(forename);
        LocalDate birthDate = LocalDate.now();
        entity.setBirthDate(birthDate);
        PartnerEntityDTOMapper mapper = new PartnerEntityDTOMapper();
        //When
        PartnerDTO dto = mapper.mapToDto(entity);
        //Then
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getForename()).isEqualTo(forename);
        assertThat(dto.getId()).isEqualTo("P" + id);
        assertThat(dto.getBirthDate()).isSameAs(birthDate);
    }
}