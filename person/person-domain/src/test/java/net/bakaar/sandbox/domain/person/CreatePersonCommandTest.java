package net.bakaar.sandbox.domain.person;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.Converter;
import junitparams.converters.Param;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitParamsRunner.class)
public class CreatePersonCommandTest {

    @Test
    public void of_only_mandatory_should_set_the_value() {
        //Given
        String name = "myName";
        String forename = "myForeName";
        LocalDate birthDate = LocalDate.now();
        //When
        CreatePersonCommand createdCommand = CreatePersonCommand.of(name, forename, birthDate);
        //Then
        assertThat(createdCommand).isNotNull();
        assertThat(createdCommand.getName()).isEqualTo(name);
        assertThat(createdCommand.getForename()).isEqualTo(forename);
        assertThat(createdCommand.getBirthDate()).isEqualTo(birthDate);
    }

    @Test
    public void of_should_set_the_value() {
        //Given
        String name = "myName";
        String forename = "myForeName";
        LocalDate birthDate = LocalDate.now();
        long number = 7564352164758L;
        //When
        CreatePersonCommand createdCommand = CreatePersonCommand.of(name, forename, birthDate, number);
        //Then
        assertThat(createdCommand).isNotNull();
        assertThat(createdCommand.getName()).isEqualTo(name);
        assertThat(createdCommand.getForename()).isEqualTo(forename);
        assertThat(createdCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdCommand.getSocialSecurityNumber()).isEqualTo(number);
    }

    @Test
    @Parameters(method = "paramsForMandatoryFileds")
    public void mandatory_fields_should_be_set(String name, String forename, @Param(converter = LocalDateConverter.class) LocalDate birthDate, String fieldName) {
        //When
        Throwable thrown = catchThrowable(() -> CreatePersonCommand.of(name, forename, birthDate));
        //Then
        assertThat(thrown).isNotNull();
        assertThat(thrown.getMessage()).contains(fieldName);
    }

    private Object paramsForMandatoryFileds() {
        return new Object[]{
                new Object[]{"name", "forename", null, "birthDate"},
                new Object[]{"name", "forename", "", "birthDate"},
                new Object[]{"name", "forename", " ", "birthDate"},
                new Object[]{"name", "forename", "31.12.9999", "birthDate"},
                new Object[]{"name", null, "16.12.1981", "forename"},
                new Object[]{"name", "", "16.12.1981", "forename"},
                new Object[]{"name", " ", "16.12.1981", "forename"},
                new Object[]{null, "forename", "16.12.1981", "name"},
                new Object[]{"", "forename", "16.12.1981", "name"},
                new Object[]{" ", "forename", "16.12.1981", "name"},
        };
    }

    public static class LocalDateConverter implements Converter<Param, LocalDate> {
        @Override
        public void initialize(Param annotation) {
        }

        @Override
        public LocalDate convert(Object param) {
            try {
                return LocalDate.parse(param.toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (Exception e) {
                return null;
            }
        }
    }
}