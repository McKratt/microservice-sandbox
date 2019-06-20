package net.bakaar.sandbox.domain.person;

import lombok.Getter;
import net.bakaar.sandbox.domain.person.address.Address;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Person {

    private final PNumber id;
    private Address mainAddress;
    private PostalLine name;
    private PostalLine forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;
    private List<Address> secondaryAddresses = new ArrayList<>();

    private Person(PNumber id, PostalLine name, PostalLine forename, LocalDate birthDate, Long socialSecurityNumber, Address address) {
        this.id = id;
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.mainAddress = address;
    }

    public static WithIdBuilder of(String name, String forename, LocalDate birthDate, Address mainAddress) {
        return new Builder(name, forename, birthDate, mainAddress);
    }

    public Person changeName(String name) {
        this.name = PostalLine.of(name);
        return this;
    }

    public Person addSecondaryAddress(Address address) {
        secondaryAddresses.add(address);
        return this;
    }

    public List<Address> getSecondaryAddresses() {
        return Collections.unmodifiableList(secondaryAddresses);
    }

    public Person relocate(Address newAddress) {
        mainAddress = newAddress;
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
        private final Address mainAddress;
        private PNumber id;
        private Long ssn;

        Builder(String name, String forename, LocalDate birthDate, Address mainAddress) {
            this.name = name;
            this.forename = forename;
            this.birthDate = birthDate;
            this.mainAddress = mainAddress;
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
            return new Person(id, PostalLine.of(name), PostalLine.of(forename), birthDate, ssn, mainAddress);
        }

    }

}
