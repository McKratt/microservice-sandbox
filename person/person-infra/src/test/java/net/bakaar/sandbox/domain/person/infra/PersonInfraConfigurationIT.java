package net.bakaar.sandbox.domain.person.infra;

import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.infra.service.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.infra.service.PersonApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersonInfraConfiguration.class)
public class PersonInfraConfigurationIT {

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
