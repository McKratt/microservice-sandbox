package net.bakaar.sandbox.domain.person.data.jpa.mapper;

import net.bakaar.sandbox.domain.address.Address;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.domain.person.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.domain.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonEntityDomainMapperTest {

    private final long id = 12345678;
    private final PNumber pNumber = PNumber.of(id);
    private final String name = "Name";
    private final String forename = "Forename";
    private final LocalDate birthDate = LocalDate.now();
    private final AddressEntityDomainMapper addressMapper = mock(AddressEntityDomainMapper.class);
    private final long number = 7541234567890L;
    private PartnerEntityDomainMapper mapper = new PartnerEntityDomainMapper(addressMapper);

    @Test
    public void mapToEntity_should_map_correctly() {
        //Given
        Address address = mock(Address.class);
        Person person = Person.of(name, forename, birthDate)
                .withId(pNumber)
                .withSocialSecurityNumber(number)
                .build()
                .addNewAddress(address);
        AddressEntity returnedAddressEntity = mock(AddressEntity.class);
        given(addressMapper.mapToEntity(address)).willReturn(returnedAddressEntity);
        //When
        PersonEntity entity = mapper.mapToEntity(person);
        //Then
        checkPersonEntityValues(entity);
        verify(addressMapper).mapToEntity(address);
        assertThat(entity.getPersonAddresses()).isNotEmpty().extracting("address").containsOnly(returnedAddressEntity);
    }

    private void checkPersonEntityValues(PersonEntity entity) {
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getForename()).isEqualTo(forename);
        assertThat(entity.getPNumber()).isEqualTo(id);
        assertThat(entity.getSocialSecurityNumber()).isEqualTo(number);
    }

    @Test
    public void mapToEntity_should_map_person_correctly() {
        // Given
        Person person = Person.of(name, forename, birthDate)
                .withId(pNumber)
                .withSocialSecurityNumber(number)
                .build();
        // Then
        PersonEntity entity = mapper.mapToEntity(person);
        checkPersonEntityValues(entity);
    }

    // TODO faire le test si le numéro de sécurité social est vide

    @Test
    public void mapToDomain_should_map_person_correctly() {
        //Given
        PersonEntity entity = createPersonEntity();
        //When
        Person domain = mapper.mapToDomain(entity);
        //Then
        checkParnterValues(domain);
    }

    private void checkParnterValues(Person domain) {
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(pNumber);
        assertThat(domain.getName().getLine()).isEqualTo(name);
        assertThat(domain.getForename().getLine()).isEqualTo(forename);
        assertThat(domain.getBirthDate()).isSameAs(birthDate);
        assertThat(domain.getSocialSecurityNumber()).isEqualTo(number);
    }

    @NotNull
    private PersonEntity createPersonEntity() {
        PersonEntity entity = new PersonEntity();
        entity.setPNumber(id);
        entity.setName(name);
        entity.setForename(forename);
        entity.setBirthDate(birthDate);
        entity.setId(98765432L);
        entity.setSocialSecurityNumber(number);
        return entity;
    }

    @Test
    public void mapToDomain_should_map_correctly() {
        // Given
        PersonEntity entity = createPersonEntity();
        PersonAddressesEntity link = new PersonAddressesEntity();
        AddressEntity addressEntity = mock(AddressEntity.class);
        link.setPerson(entity);
        link.setAddress(addressEntity);
        entity.getPersonAddresses().add(link);
        Address mappedAddress = mock(Address.class);
        given(addressMapper.mapToDomain(link)).willReturn(mappedAddress);
        // When
        Person domain = mapper.mapToDomain(entity);
        // Then
        checkParnterValues(domain);
        verify(addressMapper).mapToDomain(link);
        assertThat(domain.getSecondaryAddresses()).isNotEmpty().containsOnly(mappedAddress);
    }
}