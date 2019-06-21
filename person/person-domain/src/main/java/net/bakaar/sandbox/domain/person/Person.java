package net.bakaar.sandbox.domain.person;

import lombok.Getter;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class Person {

    private final PNumber id;
    private final Long socialSecurityNumber; // FIXME make it VO
    private PostalLine name;
    private PostalLine forename;
    private final LocalDate birthDate;
    private PersonalAddress mainAddress;
    private List<PersonalAddress> secondaryAddresses = new ArrayList<>();

    private Person(PNumber id, PostalLine name, PostalLine forename, LocalDate birthDate, Long socialSecurityNumber, PersonalAddress personalAddress) {
        this.id = id;
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.mainAddress = personalAddress;
    }

    public static WithIdBuilder of(String name, String forename, LocalDate birthDate, PersonalAddress mainPersonalAddress) {
        return new Builder(name, forename, birthDate, mainPersonalAddress);
    }

    public Person changeName(String name) {
        this.name = PostalLine.of(name);
        return this;
    }

    public Person addSecondaryAddresses(PersonalAddress... personalAddress) {
        secondaryAddresses.addAll(Arrays.asList(personalAddress));
        return this;
    }

    public List<PersonalAddress> getSecondaryAddresses() {
        return Collections.unmodifiableList(secondaryAddresses);
    }

    public Person relocate(PersonalAddress newPersonalAddress) {
        mainAddress = newPersonalAddress;
        return this;
    }

    public interface BaseBuilder {

        BaseBuilder withSocialSecurityNumber(long number);

        Person build();
    }

    public interface WithIdBuilder {
        BaseBuilder withId(PNumber id);
    }

    public static class Builder implements BaseBuilder, WithIdBuilder {
        private final String name;
        private final String forename;
        private final LocalDate birthDate;
        private final PersonalAddress mainPersonalAddress;
        private PNumber id;
        private Long ssn;

        Builder(String name, String forename, LocalDate birthDate, PersonalAddress mainPersonalAddress) {
            this.name = name;
            this.forename = forename;
            this.birthDate = birthDate;
            this.mainPersonalAddress = mainPersonalAddress;
        }

        public BaseBuilder withId(PNumber id) {
            this.id = id;
            return this;
        }

        @Override
        public BaseBuilder withSocialSecurityNumber(long number) {
            this.ssn = number;
            return this;
        }

        public Person build() {
            return new Person(id, PostalLine.of(name), PostalLine.of(forename), birthDate, ssn, mainPersonalAddress);
        }

    }

}
