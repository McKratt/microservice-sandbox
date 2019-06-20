package net.bakaar.sandbox.domain.person.cucumber.steps;

import cucumber.api.java8.En;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.infra.service.PersonApplicationService;
import net.bakaar.sandbox.domain.person.infra.service.vo.CreateAddressCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ManageAddressesStepDef extends AbstractSpringSteps implements En {

    @Autowired
    private PersonApplicationService service;

    @Autowired
    private PersonRepository personRepository;

    private PNumber givenPartnerId = PNumber.of(75645276);
    private Person testedPerson;

    public ManageAddressesStepDef() {
        Given("^an existing partner without address$", () -> {
            Person givenPerson = Person.of("name", "forename", LocalDate.now().minus(20, ChronoUnit.YEARS)).withId(givenPartnerId).build();
            personRepository.putPartner(givenPerson);

            assertThat(personRepository.fetchPartnerById(givenPartnerId)).isNotNull();
        });
        When("^I add an address to this partner$", () -> {
            CreateAddressCommand newAddressCommand = CreateAddressCommand.mainOf("Address");
            testedPerson = service.addAddressToPartner(givenPartnerId, newAddressCommand);
        });
        Then("^the partner has exactly one address$", () -> {
            assertThat(testedPerson.getSecondaryAddresses()).hasSize(1);
        });
        Then("^this address is the main address of the partner$", () -> {
            assertThat(testedPerson.getSecondaryAddresses().get(0).isMain()).isTrue();
        });
    }
}
