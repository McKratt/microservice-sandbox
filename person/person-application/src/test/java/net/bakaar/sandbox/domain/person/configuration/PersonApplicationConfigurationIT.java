package net.bakaar.sandbox.domain.person.configuration;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.PersonApplicationConfiguration;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(PersonApplicationConfiguration.class)
class PersonApplicationConfigurationIT {

    @Autowired
    private PersonApplicationService service;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @Test
    void context_should_be_complete() {
        assertThat(service).isNotNull()
                .isInstanceOf(PersonApplicationService.class);
    }
}
