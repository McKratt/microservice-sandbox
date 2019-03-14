package net.bakaar.sandbox.person.data.rest;

import net.bakaar.sandbox.person.domain.BusinessNumberRepository;
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
