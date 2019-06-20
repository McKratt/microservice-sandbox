package net.bakaar.sandbox.infra.data.rest.configuration;

import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import net.bakaar.sandbox.infra.data.rest.BusinessNumberRepositoryAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(BusinessNumberServiceProperties.class)
public class PersonDataRestConfiguration {

    @Bean
    public BusinessNumberRepository businessNumberService(BusinessNumberServiceProperties properties, RestTemplate restTemplate) {
        return new BusinessNumberRepositoryAdapter(properties, restTemplate);
    }
}
