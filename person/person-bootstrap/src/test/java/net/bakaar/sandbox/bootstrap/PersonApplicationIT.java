package net.bakaar.sandbox.bootstrap;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.rest.controller.PersonRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonApplicationIT {

    @Autowired
    private PersonRestController restController;
    @Autowired
    private BusinessNumberRepository numberService;
    //    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonApplicationService personApplicationService;


    @Test
    public void context_should_load_correctly() {
        assertThat(restController).isNotNull();
        assertThat(numberService).isNotNull();
//        assertThat(restTemplate).isNotNull();
        assertThat(personRepository).isNotNull();
        assertThat(personApplicationService).isNotNull();
    }
}
