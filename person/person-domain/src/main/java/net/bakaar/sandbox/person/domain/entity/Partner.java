package net.bakaar.sandbox.person.domain.entity;

import lombok.Getter;
import net.bakaar.sandbox.person.domain.vo.PostalLine;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Partner {

    private final PNumber id;
    private PostalLine name;
    private PostalLine forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;
    private List<Address> addresses = new ArrayList<>();

    private Partner(PNumber id, PostalLine name, PostalLine forename, LocalDate birthDate, Long socialSecurityNumber) {
        this.id = id;
        this.name = name;
        this.forename = forename;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public static WithIdBuilder of(String name, String forename, LocalDate birthDate) {
        return new Builder(name, forename, birthDate);
    }

    public Partner changeForename(String forename) {
        this.forename = PostalLine.of(forename);
        return this;
    }

    public Partner changeName(String name) {
        this.name = PostalLine.of(name);
        return this;
    }

    public Partner addNewAddress(Address address) {
        if (addresses.isEmpty()) {
            address.makeItMain();
        }
        addresses.add(address);
        return this;
    }

    public List<Address> getAddresses() {
        return addresses;
    }


    public interface BaseBuilder {

        BaseBuilder withSocialSecurityNumber(long number);

        Partner build();
    }

    public interface WithIdBuilder {
        BaseBuilder withId(PNumber id);
    }

    public static class Builder implements BaseBuilder, WithIdBuilder {
        private final String name;
        private final String forename;
        private final LocalDate birthDate;
        private PNumber id;
        private Long ssn;

        Builder(String name, String forename, LocalDate birthDate) {
            this.name = name;
            this.forename = forename;
            this.birthDate = birthDate;
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

        public Partner build() {
            return new Partner(id, PostalLine.of(name), PostalLine.of(forename), birthDate, ssn);
        }

    }

}
