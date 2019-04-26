package net.bakaar.sandbox.person.cucumber.config;

import net.bakaar.sandbox.person.domain.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class CucumberSpringConfiguration {

    @Bean
    @Primary
    public BusinessNumberRepository businessNumberRepository() {
        return () -> PNumber.of(64738965);
    }
}
