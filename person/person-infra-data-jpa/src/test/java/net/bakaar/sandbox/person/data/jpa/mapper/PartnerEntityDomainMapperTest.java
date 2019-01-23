package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerEntityDomainMapperTest {

    private final long id = 12345678;
    private final PNumber pNumber = PNumber.of(id);
    private final String name = "Name";
    private final String forename = "Forename";
    private final LocalDate birthDate = LocalDate.now();
    private PartnerEntityDomainMapper mapper = new PartnerEntityDomainMapper();

    @Test
    public void mapToEntity_should_map_correctly() {
        //Given
        Partner partner = Partner.of(pNumber, name, forename, birthDate);
        //When
        PersonEntity entity = mapper.mapToEntity(partner);
        //Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getForename()).isEqualTo(forename);
        assertThat(entity.getPNumber()).isEqualTo(id);
    }

    @Test
    public void mapToDomain_should_map_correctly() {
        //Given
        PersonEntity entity = new PersonEntity();
        entity.setPNumber(id);
        entity.setName(name);
        entity.setForename(forename);
        entity.setBirthDate(birthDate);
        entity.setId(98765432L);
        //When
        Partner domain = mapper.mapToDomain(entity);
        //Then
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(pNumber);
        assertThat(domain.getName()).isEqualTo(name);
        assertThat(domain.getForename()).isEqualTo(forename);
        assertThat(domain.getBirthDate()).isSameAs(birthDate);
    }
}