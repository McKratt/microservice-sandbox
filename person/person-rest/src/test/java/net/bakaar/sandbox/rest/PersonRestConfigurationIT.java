package net.bakaar.sandbox.rest;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.rest.controller.PersonRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(PersonRestConfiguration.class)
class PersonRestConfigurationIT {

    @Autowired
    private PersonRestController controller;

    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @MockBean
    private PersonApplicationService service;

    @Test
    void context_should_be_complete() {
        assertThat(controller).isNotNull();
    }
}