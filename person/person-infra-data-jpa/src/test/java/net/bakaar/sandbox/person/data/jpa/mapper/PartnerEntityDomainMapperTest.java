package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.domain.entity.Address;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Ignore("Partie persistence pas prête")
public class PartnerEntityDomainMapperTest {

    private final long id = 12345678;
    private final PNumber pNumber = PNumber.of(id);
    private final String name = "Name";
    private final String forename = "Forename";
    private final LocalDate birthDate = LocalDate.now();
    private final AddressEntityDomainMapper addressMapper = mock(AddressEntityDomainMapper.class);
    private PartnerEntityDomainMapper mapper = new PartnerEntityDomainMapper(addressMapper);

    @Test
    public void mapToEntity_should_map_correctly() {
        //Given
        Address address = mock(Address.class);
        Partner partner = Partner.of(name, forename, birthDate)
                .withId(pNumber)
                .build()
                .addNewAddress(address);
        AddressEntity returnedAddressEntity = mock(AddressEntity.class);
        given(addressMapper.mapToEntity(address)).willReturn(returnedAddressEntity);
        //When
        PersonEntity entity = mapper.mapToEntity(partner);
        //Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getForename()).isEqualTo(forename);
        assertThat(entity.getPNumber()).isEqualTo(id);
        verify(addressMapper).mapToEntity(address);
//        assertThat(entity.getAddresses()).isNotEmpty().containsOnly(returnedAddressEntity);
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
        assertThat(domain.getName().getLine()).isEqualTo(name);
        assertThat(domain.getForename().getLine()).isEqualTo(forename);
        assertThat(domain.getBirthDate()).isSameAs(birthDate);
    }
}