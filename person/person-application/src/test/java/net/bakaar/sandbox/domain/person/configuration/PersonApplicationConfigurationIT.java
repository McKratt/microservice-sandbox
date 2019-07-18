package net.bakaar.sandbox.domain.person.configuration;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.PersonApplicationConfiguration;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersonApplicationConfiguration.class)
public class PersonApplicationConfigurationIT {

    @Autowired
    private PersonApplicationService service;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @Test
    public void context_should_be_complete() {
        assertThat(service).isNotNull()
                .isInstanceOf(PersonApplicationService.class);
    }
}
