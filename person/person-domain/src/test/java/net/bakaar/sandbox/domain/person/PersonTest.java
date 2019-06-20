package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.person.address.Address;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PersonTest {
    private Address address = mock(Address.class);

    @Test
    public void builder_should_build_partner_without_socialNumber() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        //When
        Person createdPerson = Person.of(name, forename, birthDate, address).withId(number).build();
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getForename().getLine()).isEqualTo(forename);
        assertThat(createdPerson.getName().getLine()).isEqualTo(name);
        assertThat(createdPerson.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPerson.getId()).isEqualTo(number);
        assertThat(createdPerson.getSocialSecurityNumber()).isNull();
        assertThat(createdPerson.getMainAddress()).isSameAs(address);
        assertThat(createdPerson.getSecondaryAddresses()).isEmpty();
    }

    @Test
    public void builder_should_build_partner_with_socialNumber() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        long socialNumber = 75604537281L;
        //When
        Person createdPerson = Person.of(name, forename, birthDate, address)
                .withId(number)
                .withSocialSecurityNumber(socialNumber)
                .build();
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getForename().getLine()).isEqualTo(forename);
        assertThat(createdPerson.getName().getLine()).isEqualTo(name);
        assertThat(createdPerson.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPerson.getId()).isEqualTo(number);
        assertThat(createdPerson.getSocialSecurityNumber()).isEqualTo(socialNumber);
        assertThat(createdPerson.getMainAddress()).isSameAs(address);
        assertThat(createdPerson.getSecondaryAddresses()).isEmpty();
    }

    @Test
    public void changeName_should_update_name() {
        //Given
        String name = "myName";
        String forename = "myForename";
        String newName = "NewNames";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person toModify = Person.of(name, forename, birthDate, address)
                .withId(number)
                .build();
        //When
        assertThat(toModify.changeName(newName)).isNotNull();
        //Then
        assertThat(toModify).isNotNull();
        assertThat(toModify.getForename().getLine()).isEqualTo(forename);
        assertThat(toModify.getName().getLine()).isEqualTo(newName);
        assertThat(toModify.getBirthDate()).isEqualTo(birthDate);
        assertThat(toModify.getId()).isEqualTo(number);
        assertThat(toModify.getMainAddress()).isSameAs(address);
        assertThat(toModify.getSecondaryAddresses()).isEmpty();

    }

    @Test
    public void addSecondaryAddress_should_add_an_address_to_the_list() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(name, forename, birthDate, address)
                .withId(number)
                .build();
        Address secondaryAddress = mock(Address.class);
        assertThat(person.getSecondaryAddresses()).isEmpty();
        //When
        Person modifiedPerson = person.addSecondaryAddress(secondaryAddress);
        //Then
        assertThat(modifiedPerson).isNotNull();
        assertThat(modifiedPerson.getSecondaryAddresses()).containsOnly(secondaryAddress);
        assertThat(modifiedPerson.getMainAddress()).isSameAs(address);
    }

    @Test
    public void relocate_should_change_main_address() {
        // Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(name, forename, birthDate, address)
                .withId(number)
                .build();
        Address newAddress = mock(Address.class);
        assertThat(person.getSecondaryAddresses()).isEmpty();
        assertThat(person.getMainAddress()).isSameAs(address);
        // When
        person.relocate(newAddress);
        // Then
        assertThat(person.getMainAddress()).isSameAs(newAddress);
    }
}