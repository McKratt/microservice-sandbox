package net.bakaar.sandbox.person.cucumber.steps;

import net.bakaar.sandbox.person.application.PersonApplication;
import net.bakaar.sandbox.person.cucumber.config.CucumberSpringConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {CucumberSpringConfiguration.class, PersonApplication.class})
@ContextConfiguration
public class AbstractSpringSteps {
}
