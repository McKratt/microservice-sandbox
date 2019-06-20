package net.bakaar.sandbox.domain.person;

import lombok.Getter;

import java.time.LocalDate;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
class CreatePersonCommand {

    private final String name;
    private final String forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;

    private CreatePersonCommand(String name, String forename, LocalDate birthDate, Long socialSecurityNumber) {
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public static CreatePersonCommand of(String name, String forename, LocalDate birthDate) {
        return of(name, forename, birthDate, null);
    }

    private static void checkStringParam(String field, String fieldName) {
        if (field == null || isEmpty(field.trim())) {
            throw new IllegalArgumentException(String.format("%s should have a value", fieldName));
        }
    }

    public static CreatePersonCommand of(String name, String forename, LocalDate birthDate, Long socialSecurityNumber) {
        checkStringParam(forename, "forename");
        checkStringParam(name, "name");
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate should have a value and should not be in the future");
        }
        return new CreatePersonCommand(name, forename, birthDate, socialSecurityNumber);
    }
}
