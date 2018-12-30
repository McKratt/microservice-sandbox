package net.bakaar.sandbox.person.data;

import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.data.rest.BusinessNumberServiceProperties;
import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {PersonDataConfiguration.class})
public class PersonDataConfigurationIT {

    @Autowired
    private BusinessNumberRepository businessNumberRepository;

    @Autowired
    private BusinessNumberServiceProperties properties;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerReadStore partnerReadStore;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void context_should_load() {
        assertThat(partnerRepository).isNotNull().isSameAs(partnerReadStore);
        assertThat(personJpaRepository).isNotNull();
        assertThat(properties).isNotNull();
        assertThat(businessNumberRepository).isNotNull();
    }
}