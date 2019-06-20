package net.bakaar.sandbox.domain.person;

import lombok.Getter;
import net.bakaar.sandbox.domain.shared.AddressNumber;

import java.time.LocalDate;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
class CreatePersonCommand {

    private final String name;
    private final String forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;
    private final AddressNumber mainAddressNumber;

    private CreatePersonCommand(String name, String forename, LocalDate birthDate, Long socialSecurityNumber, AddressNumber mainAddressNumber) {
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.mainAddressNumber = mainAddressNumber;
    }

    public static CreatePersonCommand of(String name, String forename, LocalDate birthDate, AddressNumber mainAddressNumber) {
        return of(name, forename, birthDate, null, mainAddressNumber);
    }

    private static void checkStringParam(String field, String fieldName) {
        if (field == null || isEmpty(field.trim())) {
            throw new IllegalArgumentException(String.format("%s should have a value", fieldName));
        }
    }

    public static CreatePersonCommand of(String name, String forename, LocalDate birthDate, Long socialSecurityNumber, AddressNumber mainAddressNumber) {
        checkStringParam(forename, "forename");
        checkStringParam(name, "name");
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate should have a value and should not be in the future");
        }
        if (mainAddressNumber == null) {
            throw new IllegalArgumentException("mainAddressNumber is mandatory");
        }
        return new CreatePersonCommand(name, forename, birthDate, socialSecurityNumber, mainAddressNumber);
    }

}
