package net.bakaar.sandbox.cas.data.rest.config;

import net.bakaar.sandbox.cas.data.rest.BusinessIdRepositoryAdapter;
import net.bakaar.sandbox.cas.data.rest.BusinessIdServiceProperties;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class PactSpringTestConfiguration {

    public static final String TEST_URL = "http://localhost:8090/bns";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BusinessIdRepository businessIdService(RestTemplate restTemplate) {
        BusinessIdServiceProperties properties = new BusinessIdServiceProperties();
        properties.setUrl(TEST_URL);
        return new BusinessIdRepositoryAdapter(properties, restTemplate);
    }
}
