package net.bakaar.sandbox.domain.person;

import lombok.Getter;

import java.time.LocalDate;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
public class CreatePersonCommand {

    private final String name;
    private final String forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;
    private final PersonalAddress mainAddress;

    private CreatePersonCommand(String name, String forename, LocalDate birthDate, Long socialSecurityNumber, PersonalAddress mainAddress) {
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.mainAddress = mainAddress;
    }

    public static CreatePersonCommand of(String name, String forename, LocalDate birthDate, PersonalAddress mainAddressNumber) {
        return of(name, forename, birthDate, null, mainAddressNumber);
    }

    private static void checkStringParam(String field, String fieldName) {
        if (field == null || isEmpty(field.trim())) {
            throw new IllegalArgumentException(String.format("%s should have a value", fieldName));
        }
    }

    public static CreatePersonCommand of(String name, String forename, LocalDate birthDate, Long socialSecurityNumber, PersonalAddress mainAddressNumber) {
        checkStringParam(forename, "forename");
        checkStringParam(name, "name");
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate should have a value and should not be in the future");
        }
        if (mainAddressNumber == null) {
            throw new IllegalArgumentException("mainAddress is mandatory");
        }
        return new CreatePersonCommand(name, forename, birthDate, socialSecurityNumber, mainAddressNumber);
    }

}
