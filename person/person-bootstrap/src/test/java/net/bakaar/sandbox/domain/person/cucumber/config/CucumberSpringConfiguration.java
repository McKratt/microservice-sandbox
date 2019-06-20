package net.bakaar.sandbox.domain.person.cucumber.config;

import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class CucumberSpringConfiguration {

    @Bean
    @Primary
    public BusinessNumberRepository businessNumberRepository() {
        return new BusinessNumberRepository() {
            @Override
            public PNumber fetchNextPNumber() {
                return PNumber.of(64738965);
            }

            @Override
            public AddressNumber fetchNextAddressNumber() {
                return AddressNumber.of(65748394532L);
            }
        };
    }
}
