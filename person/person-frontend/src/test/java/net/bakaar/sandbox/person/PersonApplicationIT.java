package net.bakaar.sandbox.person;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import net.bakaar.sandbox.person.rest.controller.PartnerRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonApplicationIT {

  @Autowired
  private PartnerRestController restController;
  @Autowired
  private BusinessNumberRepository numberService;
  @Autowired
  @Qualifier("domainService")
  private CreatePartnerUseCase domaineService;
  @Autowired
  private RestTemplate restTemplate;

  @Test
  public void context_should_load_correctly() {
    assertThat(restController).isNotNull();
    assertThat(numberService).isNotNull();
    assertThat(domaineService).isNotNull().isInstanceOf(PersonDomaineService.class);
    assertThat(restTemplate).isNotNull();
  }
}
