package net.bakaar.sandbox.infra.data.jpa;

import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.infra.data.jpa.repository.PersonJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class)
public class PersonDataJpaConfigurationIT {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;


    @Test
    public void context_should_load() {
        assertThat(personRepository).isNotNull();
        assertThat(personJpaRepository).isNotNull();
    }
}