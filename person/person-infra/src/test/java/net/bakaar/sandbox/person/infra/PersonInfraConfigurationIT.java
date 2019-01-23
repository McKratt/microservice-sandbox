package net.bakaar.sandbox.person.infra;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.infra.service.PersonRestService;
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
    private CreatePartnerUseCase service;

    @MockBean
    private PartnerRepository partnerRepository;

    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @Test
    public void context_should_be_complete() {
        assertThat(service).isNotNull()
                .isInstanceOf(PersonRestService.class);
    }
}
