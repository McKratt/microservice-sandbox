package net.bakaar.sandbox.person.domain.cucumber.steps;

import cucumber.api.java8.En;
import net.bakaar.sandbox.event.domain.EventStore;
import net.bakaar.sandbox.person.domain.PartnerDomainService;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.event.PartnerModified;
import net.bakaar.sandbox.person.domain.vo.SearchPartnerQuery;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ModifyPartnerSteps implements En {
    private final EventStore store = mock(EventStore.class);
    private TestPartnerRepository repository = new TestPartnerRepository();
    private Partner createdPartner;

    public ModifyPartnerSteps() {
        Given("^a Partner with named \"([^\"]*)\" \"([^\"]*)\", born at (\\d+)\\.(\\d+)\\.(\\d+) and number (\\d+)$", (String forename, String name, Integer day, Integer month, Integer year, Long socialNumber) -> {
            createdPartner = Partner.of(name, forename, LocalDate.of(year, month, day)).withId(PNumber.of(12345678)).build();
            repository.putPartner(createdPartner);
        });
        When("^I change the forename of the partner to \"([^\"]*)\"$", (String forename) -> {
            Partner partnerToChange = repository.fetchPartnerById(createdPartner.getId())
                    .changeForename(forename);
            assertThat(partnerToChange).isNotNull();
            PartnerDomainService service = new PartnerDomainService(store);
            Partner modifiedPartner = service.modify(partnerToChange);
            repository.putPartner(modifiedPartner);
        });
        Then("^the partner is now \"([^\"]*)\" \"([^\"]*)\" born at (\\d+)\\.(\\d+)\\.(\\d+)$", (String forename, String name, Integer day, Integer month, Integer year) -> {
            Partner toCheck = repository.fetchPartnerById(createdPartner.getId());
            assertThat(toCheck).isNotNull();
            assertThat(toCheck.getName().getLine()).isEqualTo(name);
            assertThat(toCheck.getForename().getLine()).isEqualTo(forename);
            assertThat(toCheck.getBirthDate()).isEqualTo(LocalDate.of(year, month, day));
        });
        Then("^a modified partner event is emitted$", () -> {
            verify(store).store(any(PartnerModified.class));
        });
    }


    private static class TestPartnerRepository implements PartnerRepository {

        Map<PNumber, Partner> partners = new HashMap<>(2);

        @Override
        public Partner putPartner(Partner partner) {
            return partners.put(partner.getId(), partner);
        }

        @Override
        public Partner fetchPartnerById(PNumber id) {
            return partners.get(id);
        }

        @Override
        public List<Partner> searchPartner(SearchPartnerQuery query) {
            throw new NotImplementedException("searchPartner");
        }
    }
}
