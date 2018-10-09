package net.bakaar.sandbox.person.domain.cucumber.steps;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import net.bakaar.sandbox.person.domain.PersonDomaineService;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.repository.PartnerStore;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonCreationStepsDefinitions implements En {

    private final PartnerStore partnerStore = mock(PartnerStore.class);
    private final PersonDomaineService service = new PersonDomaineService(partnerStore);
    private Partner createdPartner;
    private Throwable thrown;

    public PersonCreationStepsDefinitions() {
        When("^I create a partner with name \"?([^\"]*)\"? and forename \"?([^\"]*)\"? born the (\\d+)\\.(\\d+)\\.(\\d+)$", (String name, String forename, Integer day, Integer month, Integer year) -> {
            LocalDate birthDate;
            if (day == 0 && month == 0 && year == 0) {
                birthDate = null;
            } else {
                birthDate = LocalDate.of(year, month, day);
            }
            thrown = catchThrowable(() -> createdPartner = service.createPartner(12345678L, name, forename, birthDate));
        });
        When("^I create a partner with the following data :$", (DataTable input) -> {
            List<String> table = input.asList(String.class);
            createdPartner = service.createPartner(12345678L, table.get(0), table.get(1), convertToDate(table.get(2)));
        });
        Then("^I should receive an error mentionning that the info ([^\"]*) is missing$", (String fieldName) -> {
            assertThat(thrown).isNotNull();
            assertThat(thrown.getMessage()).containsIgnoringCase(fieldName);
        });
        Then("^I should have a new partner stored in the system$", () -> {
            verify(partnerStore).push(createdPartner);
        });
    }

    @Before
    public void initializeMock() {
        given(partnerStore.push(any(Partner.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    private LocalDate convertToDate(String toConvert) {
        String[] dateComponent = toConvert.split("\\.");
        return LocalDate.of(parseInt(dateComponent[2]), parseInt(dateComponent[1]), parseInt(dateComponent[0]));
    }
}
