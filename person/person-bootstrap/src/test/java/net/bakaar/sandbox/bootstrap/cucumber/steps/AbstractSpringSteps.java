package net.bakaar.sandbox.bootstrap.cucumber.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import net.bakaar.sandbox.bootstrap.PersonApplication;
import net.bakaar.sandbox.bootstrap.cucumber.config.CucumberSpringConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {CucumberSpringConfiguration.class, PersonApplication.class})
@ContextConfiguration
@CucumberContextConfiguration
public class AbstractSpringSteps {
}
