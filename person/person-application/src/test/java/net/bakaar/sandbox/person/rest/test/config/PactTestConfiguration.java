package net.bakaar.sandbox.person.rest.test.config;

import net.bakaar.sandbox.person.domain.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class PactTestConfiguration {

  @Bean
  @Primary
  public BusinessNumberRepository testNumberService() {
    return () -> PNumber.of(10987654L);
  }
}
