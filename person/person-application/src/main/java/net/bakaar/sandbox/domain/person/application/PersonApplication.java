package net.bakaar.sandbox.domain.person.application;


import net.bakaar.sandbox.domain.person.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.domain.person.data.rest.PersonDataRestConfiguration;
import net.bakaar.sandbox.domain.person.infra.PersonInfraConfiguration;
import net.bakaar.sandbox.domain.person.rest.PersonRestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import({PersonInfraConfiguration.class,
        PersonRestConfiguration.class,
        PersonDataJpaConfiguration.class,
        PersonDataRestConfiguration.class})
public class PersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
