package net.bakaar.sandbox.infra.data.jpa.adapter;

import net.bakaar.sandbox.domain.address.Address;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.infra.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.infra.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.infra.data.jpa.repository.AddressJpaRepository;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class, initializers = {PersonRepositoryAdapterIT.Initializer.class})
@AutoConfigureTestDatabase(replace = NONE)
@Transactional
public class PersonRepositoryAdapterIT {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    private final Person toSave = Person.of(name, forename, birthDate)
            .withId(id)
            .withSocialSecurityNumber(number)
            .build();

    private final String name = "Testname";
    private final String forename = "Testforename";
    private final LocalDate birthDate = LocalDate.now().minus(20, ChronoUnit.YEARS);
    private final PNumber id = PNumber.of(12345678L);
    private final long number = 75612345676890L;
    @Autowired
    private PersonRepository adapter;
    @Autowired
    private AddressJpaRepository addressJpaRepository;


    // TODO put doit tester la mise à jour des champs de person mais aussi d'adresse et la suppression de cette dernière, ainsi que la liaison d'une adresse pré existante dans la base.

    @Test
    public void putPartner_should_store_a_person_in_the_db() {
        //Given
        //When
        Person saved = adapter.putPartner(toSave);
        //Then
        checkPersonValues(saved);
    }

    private void checkPersonValues(Person saved) {
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.getBirthDate()).isEqualTo(birthDate);
        assertThat(saved.getName().getLine()).isEqualTo(name);
        assertThat(saved.getForename().getLine()).isEqualTo(forename);
        assertThat(saved.getSocialSecurityNumber()).isEqualTo(number);
    }

    @Test
    public void putPartner_should_store_the_address_with_the_person() {
        // Given
        AddressNumber number = AddressNumber.of(123456789);
        String addressLine = "My AddressNumber";
        Address address = Address.of(number, addressLine);
        toSave.addNewAddress(address);
        // When
        Person saved = adapter.putPartner(toSave);
        // Then
        checkPersonValues(saved);
        assertThat(saved.getSecondaryPersonalAddresses()).isNotEmpty()
                .extracting("id", "address", "main")
                .contains(new Tuple(number, addressLine, true));
    }

    @Test
    public void putPartner_should_use_existing_address() {
        // Given
        long addressNumber = 756473824L;
        TestTransaction.flagForCommit();
        AddressEntity addressEntity = new AddressEntity();
        long id = 999L;
        addressEntity.setId(id);
        addressEntity.setNumber(addressNumber);
        String line = "My AddressLine";
        addressEntity.setAddressLine(line);
        addressJpaRepository.save(addressEntity);
        TestTransaction.end();
        AddressNumber number = AddressNumber.of(addressNumber);
        Address address = Address.of(number, line);
        toSave.addNewAddress(address);
        // When
        TestTransaction.start();
        Person saved = adapter.putPartner(toSave);
        TestTransaction.end();
        // Then
        checkPersonValues(saved);
        assertThat(saved.getSecondaryPersonalAddresses()).isNotEmpty()
                .extracting("id", "address", "main")
                .contains(new Tuple(number, line, true));
        TestTransaction.start();
        Iterable<AddressEntity> addresses = addressJpaRepository.findAll();
        assertThat(addresses).isNotEmpty()
                .hasSize(1)
                .extracting("id", "number", "addressLine")
                .contains(new Tuple(id, addressNumber, line));

    }

    //    @Test
//    public void fetchPartnerById_should_read_data_from_db() {
//        //Given
//        //When
//        //Then
//    }

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
