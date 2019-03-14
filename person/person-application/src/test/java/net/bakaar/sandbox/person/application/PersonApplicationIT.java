package net.bakaar.sandbox.person.application;

import net.bakaar.sandbox.person.domain.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.infra.service.PersonApplicationService;
import net.bakaar.sandbox.person.rest.controller.PartnerRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonApplicationIT {

    @Autowired
    private PartnerRestController restController;
    @Autowired
    private BusinessNumberRepository numberService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private PersonApplicationService personApplicationService;


    @Test
    public void context_should_load_correctly() {
        assertThat(restController).isNotNull();
        assertThat(numberService).isNotNull();
        assertThat(restTemplate).isNotNull();
        assertThat(partnerRepository).isNotNull();
        assertThat(personApplicationService).isNotNull();
    }
}
