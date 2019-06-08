package net.bakaar.sandbox.person.data.jpa.entity;

import net.bakaar.sandbox.person.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.person.data.jpa.repository.AddressJpaRepository;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class)
@Transactional
public class PersonEntityIT {

    // TODO do a test which test the cascade behaviour in the DB when we remove a person, the attached address should remain in the DB
    @Autowired
    private PersonJpaRepository repository;

    @Autowired
    private AddressJpaRepository addressJpaRepository;


    @Test
    public void save_should_not_remove_the_address() {
        TestTransaction.flagForCommit();
        //Given
        PersonEntity person = new PersonEntity();
        person.setId(1L);
        person.setPNumber(76846372657L);
        person.setBirthDate(LocalDate.now().minus(20, ChronoUnit.YEARS));
        person.setForename("Forename");
        person.setName("Name");

        AddressEntity address = new AddressEntity();
        address.setId(1L);
        address.setNumber(8695746352657L);
        address.setAddressLine("This is my Address");
        address.setNumber(758463542L);
        addressJpaRepository.save(address);
        TestTransaction.end();

        PersonAddressesEntity link = new PersonAddressesEntity();
        PersonAddressesId linkId = new PersonAddressesId();
        linkId.setAddress(address);
        linkId.setPerson(person);
        link.setId(linkId);
        address.getPersonAddresses().add(link);
        person.getPersonAddresses().add(link);
        PersonEntity response = repository.save(person);
        //When
        TestTransaction.start();
//        TestTransaction.flagForCommit();
        repository.delete(response);
        TestTransaction.end();
        //Then
        TestTransaction.start();
        AddressEntity expected = addressJpaRepository.findById(address.getId()).orElseThrow(() -> new RuntimeException("Entity not found"));
        assertThat(expected).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull().isNotEqualTo(0L);
    }
}