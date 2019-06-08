package net.bakaar.sandbox.person.domain.entity;

import net.bakaar.sandbox.person.domain.vo.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PartnerTest {

    @Test
    public void builder_should_build_partner_without_socialNumber() {
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
        assertThat(createdPartner.getSocialSecurityNumber()).isNull();
    }

    @Test
    public void builder_should_build_partner_with_socialNumber() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        long socialNumber = 75604537281L;
        //When
        Partner createdPartner = Partner.of(name, forename, birthDate)
                .withId(number)
                .withSocialSecurityNumber(socialNumber)
                .build();
        //Then
        assertThat(createdPartner).isNotNull();
        assertThat(createdPartner.getForename().getLine()).isEqualTo(forename);
        assertThat(createdPartner.getName().getLine()).isEqualTo(name);
        assertThat(createdPartner.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPartner.getId()).isEqualTo(number);
        assertThat(createdPartner.getSocialSecurityNumber()).isEqualTo(socialNumber);
    }

    @Test
    public void changeName_should_update_name() {
        //Given
        String name = "myName";
        String forename = "myForename";
        String newName = "NewNames";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Partner toModify = Partner.of(name, forename, birthDate)
                .withId(number)
                .build();
        //When
        assertThat(toModify.changeName(newName)).isNotNull();
        //Then
        assertThat(toModify).isNotNull();
        assertThat(toModify.getForename().getLine()).isEqualTo(forename);
        assertThat(toModify.getName().getLine()).isEqualTo(newName);
        assertThat(toModify.getBirthDate()).isEqualTo(birthDate);
        assertThat(toModify.getId()).isEqualTo(number);
    }

    @Test
    public void changeForename_should_update_forename() {
        //Given
        String name = "myName";
        String forename = "myForename";
        String newForename = "NewNames";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Partner toModify = Partner.of(name, forename, birthDate)
                .withId(number)
                .build();
        //When
        assertThat(toModify.changeForename(newForename)).isNotNull();
        //Then
        assertThat(toModify).isNotNull();
        assertThat(toModify.getForename().getLine()).isEqualTo(newForename);
        assertThat(toModify.getName().getLine()).isEqualTo(name);
        assertThat(toModify.getBirthDate()).isEqualTo(birthDate);
        assertThat(toModify.getId()).isEqualTo(number);
    }

    @Test
    public void addAddress_should_add_an_address_to_the_list() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Partner partner = Partner.of(name, forename, birthDate)
                .withId(number)
                .build();
        Address address = Address.of(mock(AddressNumber.class), "Test address");
        assertThat(partner.getAddresses()).isEmpty();
        //When
        Partner modifiedPartner = partner.addNewAddress(address);
        //Then
        assertThat(modifiedPartner).isNotNull();
        assertThat(modifiedPartner.getAddresses()).containsOnly(address);
    }

    @Test
    public void addAddress_should_set_address_to_main_if_it_is_the_first_one() {
        //Given
        String name = "myName";
        String forename = "myForename";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Partner partner = Partner.of(name, forename, birthDate)
                .withId(number)
                .build();
        Address address = Address.of(mock(AddressNumber.class), "Test address");
        assertThat(partner.getAddresses()).isEmpty();
        assertThat(address.isMain()).isFalse();
        //When
        Partner modifiedPartner = partner.addNewAddress(address);
        //Then
        assertThat(modifiedPartner.getAddresses().get(0).isMain()).isTrue();
    }
}