package net.bakaar.sandbox.person.cucumber.steps;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import net.bakaar.sandbox.person.application.PersonApplication;
import net.bakaar.sandbox.person.cucumber.config.CucumberSpringConfiguration;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.person.infra.service.PersonApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Slf4j
@SpringBootTest(classes = {CucumberSpringConfiguration.class, PersonApplication.class})
@ContextConfiguration
public class CreatePartnerStepDef implements En {

    @Autowired
    private PersonApplicationService service;

    private Partner createdPartner;
    private Throwable thrown;

    public CreatePartnerStepDef() {
        When("^I create a partner with name \"([^\"]*)\" and forename \"([^\"]*)\" born the (\\d+)\\.(\\d+)\\.(\\d+)$", (String name, String forename, Integer day, Integer month, Integer year) -> {
            assertThat(service).isNotNull();
            thrown = catchThrowable(() -> createdPartner = service.createPartner(CreatePartnerCommand.of(name, forename, LocalDate.of(year, month, day))));
        });
        Then("^this new partner should have an id$", () -> {
            LOG.error("Error", thrown);
            assertThat(thrown).isNull();
            assertThat(createdPartner.getId()).isNotNull();
        });
        When("^I create a partner with name \"([^\"]*)\" and forename \"([^\"]*)\" born the (\\d+)\\.(\\d+)\\.(\\d+) and a social security number (\\d+)$", (String name, String forename, Integer day, Integer month, Integer year, Long ssn) -> {
            assertThat(service).isNotNull();
            thrown = catchThrowable(() -> createdPartner = service.createPartner(CreatePartnerCommand.of(name, forename, LocalDate.of(year, month, day), ssn)));
        });
        Then("^I should receive an error mentioning that the info \"([^\"]*)\" is missing$", (String missingField) -> {
            assertThat(thrown.getMessage()).contains(missingField);
        });
    }
}
