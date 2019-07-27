package net.bakaar.sandbox.domain.person;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreatePersonCommand {

    private final String name;
    private final String forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;
    private final PersonalAddress mainAddress; // That's an actual address and not a command because it is a reference of an existing address.

    private CreatePersonCommand(String name,
                                String forename,
                                LocalDate birthDate,
                                Long socialSecurityNumber,
                                PersonalAddress mainAddress) {
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.mainAddress = mainAddress;
    }

    public static CreatePersonCommand of(String name,
                                         String forename,
                                         LocalDate birthDate,
                                         PersonalAddress mainAddress) {
        return of(name, forename, birthDate, null, mainAddress);
    }

    public static CreatePersonCommand of(String name,
                                         String forename,
                                         LocalDate birthDate,
                                         Long socialSecurityNumber,
                                         PersonalAddress mainAddress) {
        return new CreatePersonCommand(name, forename, birthDate, socialSecurityNumber, mainAddress);
    }
}
