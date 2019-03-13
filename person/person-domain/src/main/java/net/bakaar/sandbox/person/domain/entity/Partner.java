package net.bakaar.sandbox.person.domain.entity;

import lombok.Getter;
import net.bakaar.sandbox.person.domain.vo.PostalLine;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDate;

@Getter
public class Partner {

    private final PNumber id;
    private final PostalLine name;
    private final PostalLine forename;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;

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
