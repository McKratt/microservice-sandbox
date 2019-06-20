package net.bakaar.sandbox.domain.person.cucumber.steps;

import net.bakaar.sandbox.domain.person.application.PersonApplication;
import net.bakaar.sandbox.domain.person.cucumber.config.CucumberSpringConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {CucumberSpringConfiguration.class, PersonApplication.class})
@ContextConfiguration
public class AbstractSpringSteps {
}
