package net.bakaar.sandbox.person.data.jpa.adapter;

import net.bakaar.sandbox.person.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class, initializers = {PartnerRepositoryAdapterIT.Initializer.class})
@AutoConfigureTestDatabase(replace = NONE)
@Transactional
public class PartnerRepositoryAdapterIT {
//TODO with testcontainer

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
    @Autowired
    private PartnerRepository adapter;


    // TODO put doit tester la mise à jour des champs de person mais aussi d'adresse et la suppression de cette dernière, ainsi que la liaison d'une adresse pré existante dans la base.

    @Test
    public void putPartner_should_store_a_person_in_the_db() {
        //Given
        String name = "Testname";
        String forename = "Testforename";
        LocalDate birthDate = LocalDate.now().minus(20, ChronoUnit.YEARS);
        PNumber id = PNumber.of(12345678L);
        long number = 75612345676890L;
        Partner toSave = Partner.of(name, forename, birthDate)
                .withId(id)
                .withSocialSecurityNumber(number)
                .build();
        //When
        Partner saved = adapter.putPartner(toSave);
        //Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.getBirthDate()).isEqualTo(birthDate);
        assertThat(saved.getName().getLine()).isEqualTo(name);
        assertThat(saved.getForename().getLine()).isEqualTo(forename);
        assertThat(saved.getSocialSecurityNumber()).isEqualTo(number);
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
