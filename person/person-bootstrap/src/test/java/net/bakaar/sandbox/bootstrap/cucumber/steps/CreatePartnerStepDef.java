package net.bakaar.sandbox.bootstrap.cucumber.steps;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CreatePartnerStepDef extends AbstractSpringSteps implements En {

    @Autowired
    private PersonApplicationService service;

    private Person createdPerson;
    private Throwable thrown;

//    public CreatePartnerStepDef() {
//        When("^I create a partner with name \"([^\"]*)\" and forename \"([^\"]*)\" born the (\\d+)\\.(\\d+)\\.(\\d+)$", (String name, String forename, Integer day, Integer month, Integer year) -> {
//            assertThat(service).isNotNull();
//            thrown = catchThrowable(() -> createdPerson = service.createPerson(CreatePersonCommand.of(name, forename, LocalDate.of(year, month, day))));
//        });
//        Then("^this new partner should have an id$", () -> {
//            LOG.error("Error", thrown);
//            assertThat(thrown).isNull();
//            assertThat(createdPerson.getId()).isNotNull();
//        });
//        When("^I create a partner with name \"([^\"]*)\" and forename \"([^\"]*)\" born the (\\d+)\\.(\\d+)\\.(\\d+) and a social security number (\\d+)$", (String name, String forename, Integer day, Integer month, Integer year, Long ssn) -> {
//            assertThat(service).isNotNull();
//            thrown = catchThrowable(() -> createdPerson = service.createPerson(CreatePersonCommand.of(name, forename, LocalDate.of(year, month, day), ssn)));
//        });
//        Then("^I should receive an error mentioning that the info \"([^\"]*)\" is missing$", (String missingField) -> {
//            assertThat(thrown.getMessage()).contains(missingField);
//        });
//    }
}
