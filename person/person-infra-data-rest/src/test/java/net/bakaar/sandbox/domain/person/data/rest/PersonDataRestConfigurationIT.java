package net.bakaar.sandbox.domain.person.data.rest;

import net.bakaar.sandbox.domain.person.infra.service.BusinessNumberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersonDataRestConfiguration.class})
public class PersonDataRestConfigurationIT {

    @Autowired
    private BusinessNumberRepository businessNumberRepository;

    @Autowired
    private BusinessNumberServiceProperties properties;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void context_should_load() {
        assertThat(properties).isNotNull();
        assertThat(businessNumberRepository).isNotNull();
        assertThat(getField(businessNumberRepository, "restTemplate")).isSameAs(restTemplate);
    }
}