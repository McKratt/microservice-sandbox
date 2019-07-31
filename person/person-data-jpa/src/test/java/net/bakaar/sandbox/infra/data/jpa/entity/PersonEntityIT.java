package net.bakaar.sandbox.infra.data.jpa.entity;

import net.bakaar.sandbox.infra.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.infra.data.jpa.repository.AddressJpaRepository;
import net.bakaar.sandbox.infra.data.jpa.repository.PersonJpaRepository;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class, initializers = {PersonEntityIT.Initializer.class})
@AutoConfigureTestDatabase(replace = NONE)
@Transactional
@Ignore("should be replaced by the adapter integration test")
public class PersonEntityIT {

    @Autowired
    private PersonJpaRepository repository;

    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @Test
    public void delete_should_not_remove_the_address() {
        TestTransaction.flagForCommit();
        //Given

        PersonalAddressEntity address = createAddressEntity();
        addressJpaRepository.save(address);

        PersonEntity person = createPersonEntity();

        createLink(address, person);

        PersonEntity response = repository.save(person);
        TestTransaction.end();
        //When
        TestTransaction.start();
        TestTransaction.flagForCommit();
        repository.delete(response);
        TestTransaction.end();
        //Then
        TestTransaction.start();
        PersonalAddressEntity expected = addressJpaRepository.findById(address.getId()).orElseThrow(() -> new RuntimeException("Entity not found"));
        assertThat(expected).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull().isNotEqualTo(0L);
    }

    private void createLink(PersonalAddressEntity address, PersonEntity person) {
        PersonAddressesEntity link = new PersonAddressesEntity();
        link.setPerson(person);
        link.setAddress(address);
        link.setId(1L);
        address.getPersonAddresses().add(link);
        person.getPersonAddresses().add(link);
    }

    private PersonalAddressEntity createAddressEntity() {
        PersonalAddressEntity address = new PersonalAddressEntity();
        address.setId(1L);
        address.setNumber(8695746352657L);
        address.setAddressLine("This is my AddressNumber");
        address.setNumber(758463542L);
        return address;
    }

    private PersonEntity createPersonEntity() {
        PersonEntity person = new PersonEntity();
        person.setId(1L);
        person.setPNumber(76846372657L);
        person.setBirthDate(LocalDate.now().minus(20, ChronoUnit.YEARS));
        person.setForename("Forename");
        person.setName("Name");
        return person;
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}