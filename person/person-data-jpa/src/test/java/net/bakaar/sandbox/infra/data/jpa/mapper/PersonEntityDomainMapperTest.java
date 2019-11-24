package net.bakaar.sandbox.infra.data.jpa.mapper;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.person.SocialSecurityNumber;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonalAddressEntity;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonEntityDomainMapperTest {

    private final long id = 12345678;
    private final PNumber pNumber = PNumber.of(id);
    private final String name = "Name";
    private final String forename = "Forename";
    private final LocalDate birthDate = LocalDate.now();
    private final AddressEntityDomainMapper addressMapper = mock(AddressEntityDomainMapper.class);
    private final long number = 7561234567890L;
    private final PersonalAddress address = mock(PersonalAddress.class);
    private PersonEntityDomainMapper mapper = new PersonEntityDomainMapper(addressMapper);

    @Test
    void mapToEntity_should_map_correctly() {
        //Given
        Person person = Person.of(name, forename, birthDate, address)
                .withId(pNumber)
                .withSocialSecurityNumber(SocialSecurityNumber.of(number))
                .build();
        PersonalAddressEntity returnedPersonalAddressEntity = mock(PersonalAddressEntity.class);
        given(addressMapper.mapToEntity(address)).willReturn(returnedPersonalAddressEntity);
        //When
        PersonEntity entity = mapper.mapToEntity(person);
        //Then
        checkPersonEntityValues(entity);
        verify(addressMapper).mapToEntity(address);
        assertThat(entity.getPersonAddresses()).isNotEmpty()
                .extracting("address")
                .containsOnly(returnedPersonalAddressEntity);
        assertThat(entity.getSocialSecurityNumber()).isEqualTo(number);
    }

    private void checkPersonEntityValues(PersonEntity entity) {
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getForename()).isEqualTo(forename);
        assertThat(entity.getNumber()).isEqualTo(id);
    }

    @Test
    void mapToEntity_should_map_correctly_without_sn() {
        // Given
        Person person = Person.of(name, forename, birthDate, address)
                .withId(pNumber)
                .build();
        // When
        PersonEntity entity = mapper.mapToEntity(person);
        // Then
        checkPersonEntityValues(entity);
        assertThat(entity.getSocialSecurityNumber()).isNull();
    }

    private void checkPersonValues(Person domain) {
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(pNumber);
        assertThat(domain.getName().getLine()).isEqualTo(name);
        assertThat(domain.getForename().getLine()).isEqualTo(forename);
        assertThat(domain.getBirthDate()).isSameAs(birthDate);
        assertThat(domain.getSocialSecurityNumber().value()).isEqualTo(number);
    }

    @NotNull
    private PersonEntity createPersonEntity() {
        PersonEntity entity = new PersonEntity();
        entity.setNumber(id);
        entity.setName(name);
        entity.setForename(forename);
        entity.setBirthDate(birthDate);
        entity.setId(98765432L);
        entity.setSocialSecurityNumber(number);
        return entity;
    }

    @Test
    void mapToDomain_should_map_correctly() {
        // Given
        PersonEntity entity = createPersonEntity();
        PersonAddressesEntity link = new PersonAddressesEntity();
        PersonalAddressEntity personalAddressEntity = mock(PersonalAddressEntity.class);
        link.setPerson(entity);
        link.setAddress(personalAddressEntity);
        link.setMain(true);
        entity.getPersonAddresses().add(link);
        PersonalAddress mappedAddress = mock(PersonalAddress.class);
        given(addressMapper.mapToDomain(personalAddressEntity)).willReturn(mappedAddress);
        // When
        Person domain = mapper.mapToDomain(entity);
        // Then
        checkPersonValues(domain);
        verify(addressMapper).mapToDomain(personalAddressEntity);
        assertThat(domain.getMainAddress()).isNotNull().isEqualTo(mappedAddress);
    }

    // TODO do a test with secondary addresses
}