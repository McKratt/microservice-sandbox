package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PersonTest {
    private PersonalAddress personalAddress = mock(PersonalAddress.class);

    private static Stream<Arguments> provideArgumentForMandatoryCheck() {
        return Stream.of(
                Arguments.of("", "forename", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class)),
                Arguments.of(null, "forename", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class)),
                Arguments.of("name", "", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class)),
                Arguments.of("name", null, LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class)),
                Arguments.of("name", null, LocalDate.now().plus(3, ChronoUnit.DAYS), mock(PersonalAddress.class), mock(PNumber.class)),
                Arguments.of("name", "forename", LocalDate.now(), null, mock(PNumber.class)),
                Arguments.of("name", "forename", LocalDate.now(), mock(PersonalAddress.class), null)
        );
    }

    @Test
    void builder_should_build_person_without_socialNumber() {
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
        assertThat(createdPerson.getMainAddress()).isSameAs(personalAddress);
        assertThat(createdPerson.getSecondaryAddresses()).isEmpty();
    }

    @Test
    void builder_should_build_person_with_socialNumber() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        SocialSecurityNumber socialNumber = mock(SocialSecurityNumber.class);
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
        assertThat(createdPerson.getMainAddress()).isSameAs(personalAddress);
        assertThat(createdPerson.getSecondaryAddresses()).isEmpty();
    }

    @Test
    void changeName_should_update_name() {
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
        assertThat(toModify.getMainAddress()).isSameAs(personalAddress);
        assertThat(toModify.getSecondaryAddresses()).isEmpty();

    }

    @Test
    void addSecondaryAddress_should_add_an_address_to_the_list() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(name, forename, birthDate, personalAddress)
                .withId(number)
                .build();
        PersonalAddress secondaryPersonalAddress = mock(PersonalAddress.class);
        assertThat(person.getSecondaryAddresses()).isEmpty();
        //When
        Person modifiedPerson = person.addSecondaryAddresses(secondaryPersonalAddress);
        //Then
        assertThat(modifiedPerson).isNotNull();
        assertThat(modifiedPerson.getSecondaryAddresses()).containsOnly(secondaryPersonalAddress);
        assertThat(modifiedPerson.getMainAddress()).isSameAs(personalAddress);
    }

    @Test
    void relocate_should_change_main_address() {
        // Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(name, forename, birthDate, personalAddress)
                .withId(number)
                .build();
        PersonalAddress newPersonalAddress = mock(PersonalAddress.class);
        assertThat(person.getSecondaryAddresses()).isEmpty();
        assertThat(person.getMainAddress()).isSameAs(personalAddress);
        // When
        person.relocate(newPersonalAddress);
        // Then
        assertThat(person.getMainAddress()).isSameAs(newPersonalAddress);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentForMandatoryCheck")
    void mandatory_fields_should_throw_exception_when_not_correct(String name, String forename, LocalDate birthDate, PersonalAddress mainAddress, PNumber id) {
        // Given
        // When
        Assertions.assertThrows(IllegalArgumentException.class, () -> Person.of(name, forename, birthDate, mainAddress).withId(id).build());
        // Then
    }
}