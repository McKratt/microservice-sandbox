package net.bakaar.sandbox.person.domain.cucumber.steps;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomainService;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

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

    private final PartnerRepository partnerRepository = mock(PartnerRepository.class);
    private final BusinessNumberRepository businessNumberRepository = mock(BusinessNumberRepository.class);
    private final CreatePartnerUseCase service = new PersonDomainService(partnerRepository, businessNumberRepository);
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
            CreatePartnerCommand command = new CreatePartnerCommand(name, forename, birthDate);
            thrown = catchThrowable(() -> createdPartner = service.createPartner(command));
        });
        When("^I create a partner with the following data :$", (DataTable input) -> {
            List<String> table = input.asList(String.class);
            CreatePartnerCommand command = new CreatePartnerCommand(table.get(0), table.get(1), convertToDate(table.get(2)));
            createdPartner = service.createPartner(command);
        });
        Then("^I should receive an error mentionning that the info ([^\"]*) is missing$", (String fieldName) -> {
            assertThat(thrown).isNotNull();
            assertThat(thrown.getMessage()).containsIgnoringCase(fieldName);
        });
        Then("^I should have a new partner stored in the system$", () -> {
            verify(partnerRepository).push(createdPartner);
        });
    }

    @Before
    public void initializeMock() {
        given(partnerRepository.push(any(Partner.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        given(businessNumberRepository.createPartnerNumber()).willReturn(PNumber.of(12345678L));
    }

    private LocalDate convertToDate(String toConvert) {
        String[] dateComponent = toConvert.split("\\.");
        return LocalDate.of(parseInt(dateComponent[2]), parseInt(dateComponent[1]), parseInt(dateComponent[0]));
    }
}
