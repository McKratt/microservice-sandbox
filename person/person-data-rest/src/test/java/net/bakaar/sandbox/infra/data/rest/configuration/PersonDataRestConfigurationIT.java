package net.bakaar.sandbox.infra.data.rest.configuration;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({PersonDataRestConfiguration.class})
class PersonDataRestConfigurationIT {

    @Autowired
    private BusinessNumberRepository businessNumberRepository;
// TODO when BNS endpoint will go back
//    @Autowired
//    private BusinessNumberServiceProperties properties;
//
//    @MockBean
//    private RestTemplate restTemplate;

    @Test
    void context_should_load() {
//        assertThat(properties).isNotNull();
        assertThat(businessNumberRepository).isNotNull();
//        assertThat(getField(businessNumberRepository, "restTemplate")).isSameAs(restTemplate);
    }
}