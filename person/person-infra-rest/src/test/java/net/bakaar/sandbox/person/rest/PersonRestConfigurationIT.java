package net.bakaar.sandbox.person.rest;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.controller.PartnerRestController;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.person.rest.service.PartnerReadService;
import net.bakaar.sandbox.person.rest.service.PersonRestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersonRestConfiguration.class)
public class PersonRestConfigurationIT {

    @Autowired
    private PartnerRestController controller;

    @Autowired
    @Qualifier("readStoreApplicationService")
    private PartnerReadStore readStore;

    @Autowired
    @Qualifier("createPartnerApplicationService")
    private CreatePartnerUseCase service;


    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @MockBean(name = "domainService")
    private CreatePartnerUseCase partnerUseCase;

    @MockBean(name = "readStoreAdapter")
    private PartnerReadStore readRepository;

    @Test
    public void context_should_be_complete() {
        assertThat(controller).isNotNull();
        assertThat(service).isNotNull().isInstanceOf(PersonRestService.class);
        assertThat(readStore).isNotNull().isInstanceOf(PartnerReadService.class);
    }
}