package net.bakaar.sandbox.infra.data.jpa.adapter;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.infra.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonalAddressEntity;
import net.bakaar.sandbox.infra.data.jpa.repository.AddressJpaRepository;
import net.bakaar.sandbox.infra.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.assertj.core.groups.Tuple;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class, initializers = {PersonRepositoryAdapterIT.Initializer.class})
@AutoConfigureTestDatabase(replace = NONE) // Don't take H2, wait for in class configuration
@Transactional // Allow you handily manage transactions
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD) //reset DB after each test
public class PersonRepositoryAdapterIT {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    private final String name = "Testname";
    private final String forename = "Testforename";
    private final LocalDate birthDate = LocalDate.now().minus(20, ChronoUnit.YEARS);
    private final PNumber id = PNumber.of(12345678L);
    private final long socialSecurityNumber = 75612345676890L;
    private final AddressNumber addressNumber = AddressNumber.of(123456789);
    private final String addressLine = "My Address";
    private final PersonalAddress address = PersonalAddress.of(addressNumber, addressLine);

    // TODO put a little bit of random in this Person creation
    private final Person toSave = Person.of(name, forename, birthDate, address)
            .withId(id)
            .withSocialSecurityNumber(socialSecurityNumber)
            .build();
    @Autowired
    private PersonRepository adapter;
    @Autowired
    private AddressJpaRepository addressJpaRepository;
    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Test
    public void putPerson_should_store_a_person_in_the_db() {
        //Given
        //When
        Person saved = adapter.putPerson(toSave);
        //Then
        checkPersonValues(saved);
    }

    private void checkPersonValues(Person saved) {
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.getBirthDate()).isEqualTo(birthDate);
        assertThat(saved.getName().getLine()).isEqualTo(name);
        assertThat(saved.getForename().getLine()).isEqualTo(forename);
        assertThat(saved.getSocialSecurityNumber()).isEqualTo(socialSecurityNumber);
    }

    @Test
    public void putPerson_should_store_the_address_with_the_person() {
        // Given
        // When
        Person saved = adapter.putPerson(toSave);
        // Then
        checkPersonValues(saved);
        assertThat(saved.getMainAddress()).isNotNull()
                .extracting("id", "address")
                .contains(addressNumber, addressLine);
    }

    @Test
    public void putPerson_should_use_existing_address() {
        // Given
        TestTransaction.flagForCommit();
        PersonalAddressEntity returnedEntity = createAddressEntity();
        TestTransaction.end();
        // When
        TestTransaction.start();
        Person saved = adapter.putPerson(toSave);
        TestTransaction.end();
        // Then
        checkPersonValues(saved);
        assertThat(saved.getMainAddress()).isNotNull()
                .extracting("id", "address")
                .contains(addressNumber, addressLine);
        TestTransaction.start();
        Iterable<PersonalAddressEntity> addresses = addressJpaRepository.findAll();
        assertThat(addresses).isNotEmpty()
                .hasSize(1)
                .extracting("id", "number", "addressLine")
                .contains(new Tuple(returnedEntity.getId(), addressNumber.getValue(), addressLine));
    }

    private PersonalAddressEntity createAddressEntity() {
        PersonalAddressEntity personalAddressEntity = new PersonalAddressEntity();
        personalAddressEntity.setNumber(addressNumber.getValue());
        personalAddressEntity.setAddressLine(addressLine);
        return addressJpaRepository.save(personalAddressEntity);
    }

    @Test
    public void fetchPersonById_should_read_data_from_db() {
        //Given
        TestTransaction.flagForCommit();
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBirthDate(birthDate);
        personEntity.setForename(forename);
        personEntity.setName(name);
        personEntity.setPNumber(id.getValue());
        personEntity.setSocialSecurityNumber(socialSecurityNumber);
        PersonalAddressEntity personalAddressEntity = createAddressEntity();
        PersonAddressesEntity link = new PersonAddressesEntity();
        link.setAddress(personalAddressEntity);
        link.setPerson(personEntity);
        link.setMain(true);
        personEntity.getPersonAddresses().add(link);
        personalAddressEntity.getPersonAddresses().add(link);
        personJpaRepository.save(personEntity);
        TestTransaction.end();
        //When
        TestTransaction.start();
        Person returnedPerson = adapter.fetchPersonById(id);
        //Then
        checkPersonValues(returnedPerson);
        assertThat(returnedPerson.getMainAddress()).isNotNull()
                .extracting("address")
                .contains(addressLine);
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
