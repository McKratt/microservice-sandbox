package net.bakaar.sandbox.person.cucumber.steps;

import cucumber.api.java8.En;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.infra.service.PersonApplicationService;
import net.bakaar.sandbox.person.infra.service.vo.CreateAddressCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ManageAddressesStepDef extends AbstractSpringSteps implements En {

    @Autowired
    private PersonApplicationService service;

    @Autowired
    private PartnerRepository partnerRepository;

    private PNumber givenPartnerId = PNumber.of(75645276);
    private Partner testedPartner;

    public ManageAddressesStepDef() {
        Given("^an existing partner without address$", () -> {
            Partner givenPartner = Partner.of("name", "forename", LocalDate.now().minus(20, ChronoUnit.YEARS)).withId(givenPartnerId).build();
            partnerRepository.putPartner(givenPartner);

            assertThat(partnerRepository.fetchPartnerById(givenPartnerId)).isNotNull();
        });
        When("^I add an address to this partner$", () -> {
            CreateAddressCommand newAddressCommand = CreateAddressCommand.mainOf("Address");
            testedPartner = service.addAddressToPartner(givenPartnerId, newAddressCommand);
        });
        Then("^the partner has exactly one address$", () -> {
            assertThat(testedPartner.getAddresses()).hasSize(1);
        });
        Then("^this address is the main address of the partner$", () -> {
            assertThat(testedPartner.getAddresses().get(0).isMain()).isTrue();
        });
    }
}
