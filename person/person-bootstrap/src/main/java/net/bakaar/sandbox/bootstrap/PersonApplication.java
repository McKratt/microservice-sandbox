package net.bakaar.sandbox.bootstrap;


import net.bakaar.sandbox.domain.person.configuration.PersonApplicationConfiguration;
import net.bakaar.sandbox.infra.data.jpa.PersonDataJpaConfiguration;
import net.bakaar.sandbox.infra.data.rest.configuration.PersonDataRestConfiguration;
import net.bakaar.sandbox.rest.PersonRestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({PersonApplicationConfiguration.class,
        PersonRestConfiguration.class,
        PersonDataJpaConfiguration.class,
        PersonDataRestConfiguration.class})
public class PersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class);
    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
