package net.bakaar.sandbox.infra.data.rest.configuration;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersonDataRestConfiguration.class})
public class PersonDataRestConfigurationIT {

    @Autowired
    private BusinessNumberRepository businessNumberRepository;
// TODO when BNS endpoint will go bakc
//    @Autowired
//    private BusinessNumberServiceProperties properties;
//
//    @MockBean
//    private RestTemplate restTemplate;

    @Test
    public void context_should_load() {
//        assertThat(properties).isNotNull();
        assertThat(businessNumberRepository).isNotNull();
//        assertThat(getField(businessNumberRepository, "restTemplate")).isSameAs(restTemplate);
    }
}