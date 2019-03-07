package net.bakaar.sandbox.person.domain.entity;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerTest {

    @Test
    public void builder_should_build_correct_partner() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        //When
        Partner createdPartner = Partner.of(name, forename, birthDate).withId(number).build();
        //Then
        assertThat(createdPartner).isNotNull();
        assertThat(createdPartner.getForename().getLine()).isEqualTo(forename);
        assertThat(createdPartner.getName().getLine()).isEqualTo(name);
        assertThat(createdPartner.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPartner.getId()).isEqualTo(number);
    }
}