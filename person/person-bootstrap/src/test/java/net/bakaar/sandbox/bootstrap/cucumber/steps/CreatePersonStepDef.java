package net.bakaar.sandbox.bootstrap.cucumber.steps;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Slf4j
public class CreatePersonStepDef extends AbstractSpringSteps implements En {

    @Autowired
    private PersonApplicationService service;

    private Person createdPerson;
    private Throwable thrown;

    public CreatePersonStepDef() {
        When("^I create a valid person$", () -> {
            assertThat(service).isNotNull();

            thrown = catchThrowable(() -> createdPerson = service.createPerson(createPersonCommand(false)));
        });
        Then("^this new person should have an id$", () -> {
            LOG.error("Error", thrown);
            assertThat(thrown).isNull();
            assertThat(createdPerson.getId()).isNotNull();
        });
        When("^I create a valid person with a social number$", () -> {
            assertThat(service).isNotNull();

            thrown = catchThrowable(() -> createdPerson = service.createPerson(createPersonCommand(true)));
        });
        Then("^I should receive an error mentioning that the mandatory field \"([^\"]*)\" is missing$", (String missingField) -> {
            assertThat(thrown.getMessage()).contains(missingField);
        });
        When("^I create a person with name \"(.*)\" and forename \"(.*)\" born the (\\d+)\\.(\\d+)\\.(\\d+) and located at \"(.*)\"$",
                (String name, String forename, Integer day, Integer month, Integer year, String address) -> {
                    thrown = catchThrowable(() -> createdPerson = service.createPerson(CreatePersonCommand
                            .of(name, forename, LocalDate.of(year, month, day),
                                    PersonalAddress.of(AddressNumber.of(865745312L), address))));
                });
    }

    private CreatePersonCommand createPersonCommand(boolean hasSSN) {
        CreatePersonCommand command;
        if (hasSSN) {
            command = CreatePersonCommand
                    .of("Name", "Forename", LocalDate.of(1925, 3, 15), 7561234567890L,
                            PersonalAddress.of(AddressNumber.of(865745312L), "My Address"));
        } else {
            command = CreatePersonCommand
                    .of("Name", "Forename", LocalDate.of(1925, 3, 15),
                            PersonalAddress.of(AddressNumber.of(865745312L), "My Address"));
        }
        return command;
    }
}
