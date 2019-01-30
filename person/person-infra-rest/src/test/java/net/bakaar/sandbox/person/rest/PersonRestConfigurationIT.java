package net.bakaar.sandbox.person.rest;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.controller.PartnerRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersonRestConfiguration.class)
public class PersonRestConfigurationIT {

    @Autowired
    private PartnerRestController controller;

    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @MockBean
    private PartnerRepository partnerRepository;

    @MockBean
    private CreatePartnerUseCase createPartnerUseCase;

    @Test
    public void context_should_be_complete() {
        assertThat(controller).isNotNull();
    }
}