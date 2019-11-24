package net.bakaar.sandbox.domain.person;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CreatePersonCommandTest {

    private PersonalAddress mainAddress = mock(PersonalAddress.class);

    @Test
    void of_only_mandatory_should_set_the_value() {
        //Given
        String name = "myName";
        String forename = "myForeName";
        LocalDate birthDate = LocalDate.now();
        //When
        CreatePersonCommand createdCommand = CreatePersonCommand.of(name, forename, birthDate, mainAddress);
        //Then
        assertThat(createdCommand).isNotNull();
        assertThat(createdCommand.getName()).isEqualTo(name);
        assertThat(createdCommand.getForename()).isEqualTo(forename);
        assertThat(createdCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdCommand.getMainAddress()).isSameAs(mainAddress);
    }

    @Test
    void of_should_set_the_value() {
        //Given
        String name = "myName";
        String forename = "myForeName";
        LocalDate birthDate = LocalDate.now();
        long number = 7564352164758L;
        //When
        CreatePersonCommand createdCommand = CreatePersonCommand.of(name, forename, birthDate, number, mainAddress);
        //Then
        assertThat(createdCommand).isNotNull();
        assertThat(createdCommand.getName()).isEqualTo(name);
        assertThat(createdCommand.getForename()).isEqualTo(forename);
        assertThat(createdCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdCommand.getSocialSecurityNumber()).isEqualTo(number);
        assertThat(createdCommand.getMainAddress()).isSameAs(mainAddress);
    }
}
