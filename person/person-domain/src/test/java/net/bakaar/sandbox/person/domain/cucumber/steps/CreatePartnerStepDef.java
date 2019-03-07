package net.bakaar.sandbox.person.domain.cucumber.steps;

import cucumber.api.java8.En;
import net.bakaar.sandbox.person.domain.PartnerFactory;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CreatePartnerStepDef implements En {


    private Partner createdPartner;
    private final PartnerFactory factory = new PartnerFactory(() -> PNumber.of(12345678));
    private Throwable thrown;

    public CreatePartnerStepDef() {
        When("^I create a partner with name \"([^\"]*)\" and forename \"([^\"]*)\" born the (\\d+)\\.(\\d+)\\.(\\d+)$", (String name, String forename, Integer day, Integer month, Integer year) -> {
            thrown = catchThrowable(() -> createdPartner = factory.createPartner(CreatePartnerCommand.of(name, forename, LocalDate.of(year, month, day))));
        });
        Then("^this new partner should have an id$", () -> {
            assertThat(thrown).isNull();
            assertThat(createdPartner.getId()).isNotNull();
        });
        When("^I create a partner with name \"([^\"]*)\" and forename \"([^\"]*)\" born the (\\d+)\\.(\\d+)\\.(\\d+) and a social security number (\\d+)$", (String name, String forename, Integer day, Integer month, Integer year, Long ssn) -> {
            thrown = catchThrowable(() -> createdPartner = factory.createPartner(CreatePartnerCommand.of(name, forename, LocalDate.of(year, month, day), ssn)));
        });
        Then("^I should receive an error mentioning that the info \"([^\"]*)\" is missing$", (String missingField) -> {
            assertThat(thrown.getMessage()).contains(missingField);
        });
    }
}
