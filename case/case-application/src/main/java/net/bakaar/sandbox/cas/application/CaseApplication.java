package net.bakaar.sandbox.cas.application;

import net.bakaar.sandbox.cas.data.jpa.CaseDataJpaConfiguration;
import net.bakaar.sandbox.cas.data.rest.CaseDataRestConfiguration;
import net.bakaar.sandbox.cas.infra.CaseInfraConfiguration;
import net.bakaar.sandbox.cas.rest.CaseRestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import({CaseInfraConfiguration.class,
        CaseRestConfiguration.class,
        CaseDataJpaConfiguration.class,
        CaseDataRestConfiguration.class})
public class CaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
