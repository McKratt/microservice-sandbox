package net.bakaar.sandbox.person.data.jpa;

import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {PersonDataJpaConfiguration.class})
public class PersonDataJpaConfigurationIT {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;


    @Test
    public void context_should_load() {
        assertThat(partnerRepository).isNotNull();
        assertThat(personJpaRepository).isNotNull();
    }
}