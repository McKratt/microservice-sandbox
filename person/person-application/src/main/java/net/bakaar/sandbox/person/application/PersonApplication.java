package net.bakaar.sandbox.person.application;


import net.bakaar.sandbox.person.data.PersonDataConfiguration;
import net.bakaar.sandbox.person.infra.PersonInfraConfiguration;
import net.bakaar.sandbox.person.rest.PersonRestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import({PersonInfraConfiguration.class, PersonRestConfiguration.class, PersonDataConfiguration.class})
public class PersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
