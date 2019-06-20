package net.bakaar.sandbox.rest.test.config;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class PactTestConfiguration {

    @Bean
    @Primary
    public BusinessNumberRepository testNumberService() {
        return new BusinessNumberRepository() {
            @Override
            public PNumber fetchNextPNumber() {
                return PNumber.of(10987654L);
            }

            @Override
            public AddressNumber fetchNextAddressNumber() {
                return null;
            }
        };
    }

}
