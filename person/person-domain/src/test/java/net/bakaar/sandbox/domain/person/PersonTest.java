package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PersonTest {
    private PersonalAddress personalAddress = mock(PersonalAddress.class);

    @Test
    public void builder_should_build_partner_without_socialNumber() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        //When
        Person createdPerson = Person.of(name, forename, birthDate, personalAddress).withId(number).build();
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getForename().getLine()).isEqualTo(forename);
        assertThat(createdPerson.getName().getLine()).isEqualTo(name);
        assertThat(createdPerson.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPerson.getId()).isEqualTo(number);
        assertThat(createdPerson.getSocialSecurityNumber()).isNull();
        assertThat(createdPerson.getMainPersonalAddress()).isSameAs(personalAddress);
        assertThat(createdPerson.getSecondaryPersonalAddresses()).isEmpty();
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
        Person createdPerson = Person.of(name, forename, birthDate, personalAddress)
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
        assertThat(createdPerson.getMainPersonalAddress()).isSameAs(personalAddress);
        assertThat(createdPerson.getSecondaryPersonalAddresses()).isEmpty();
    }

    @Test
    public void changeName_should_update_name() {
        //Given
        String name = "myName";
        String forename = "myForename";
        String newName = "NewNames";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person toModify = Person.of(name, forename, birthDate, personalAddress)
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
        assertThat(toModify.getMainPersonalAddress()).isSameAs(personalAddress);
        assertThat(toModify.getSecondaryPersonalAddresses()).isEmpty();

    }

    @Test
    public void addSecondaryAddress_should_add_an_address_to_the_list() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(name, forename, birthDate, personalAddress)
                .withId(number)
                .build();
        PersonalAddress secondaryPersonalAddress = mock(PersonalAddress.class);
        assertThat(person.getSecondaryPersonalAddresses()).isEmpty();
        //When
        Person modifiedPerson = person.addSecondaryAddress(secondaryPersonalAddress);
        //Then
        assertThat(modifiedPerson).isNotNull();
        assertThat(modifiedPerson.getSecondaryPersonalAddresses()).containsOnly(secondaryPersonalAddress);
        assertThat(modifiedPerson.getMainPersonalAddress()).isSameAs(personalAddress);
    }

    @Test
    public void relocate_should_change_main_address() {
        // Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(name, forename, birthDate, personalAddress)
                .withId(number)
                .build();
        PersonalAddress newPersonalAddress = mock(PersonalAddress.class);
        assertThat(person.getSecondaryPersonalAddresses()).isEmpty();
        assertThat(person.getMainPersonalAddress()).isSameAs(personalAddress);
        // When
        person.relocate(newPersonalAddress);
        // Then
        assertThat(person.getMainPersonalAddress()).isSameAs(newPersonalAddress);
    }
}