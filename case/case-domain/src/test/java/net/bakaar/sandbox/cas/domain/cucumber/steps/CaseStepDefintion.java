package net.bakaar.sandbox.cas.domain.cucumber.steps;

import cucumber.api.java8.En;
import net.bakaar.sandbox.cas.domain.CaseService;
import net.bakaar.sandbox.cas.domain.command.CreateCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.domain.event.CaseCreated;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import net.bakaar.sandbox.cas.domain.repository.CaseRepository;
import net.bakaar.sandbox.event.domain.EventStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CaseStepDefintion implements En {

    private EventStore publisher = mock(EventStore.class);
    private CaseRepository repository = mock(CaseRepository.class);
    private BusinessIdRepository businessIdRepository = mock(BusinessIdRepository.class);
    private CaseService service = new CaseService(publisher, repository, businessIdRepository);
    private ArgumentCaptor<CaseCreated> eventArgumentCaptor = ArgumentCaptor.forClass(CaseCreated.class);
    private Case aCase;

    public CaseStepDefintion() {

        When("^we create a case with a Partner number (.+)$", (String pnummer) -> {
            given(repository.save(any(Case.class))).willAnswer(invocation -> invocation.getArgument(0));
            given(businessIdRepository.generateId()).willReturn(UUID.randomUUID().toString());
            Throwable throwable = catchThrowable(() -> aCase = this.service.createCase(new CreateCaseCommand(PNumber.of(pnummer))));
            verify(repository).save(any(Case.class));
            verify(publisher).store(eventArgumentCaptor.capture());
            assertThat(throwable).isNull();
        });

        Then("^an Event mentioning the new case is emitted$", () -> {
            CaseCreated event = eventArgumentCaptor.getValue();
            assertThat(event.getId()).isEqualTo(aCase.getId());
            assertThat(event.getPNumber()).isEqualTo(aCase.getInjured());
        });

        Then("^this Case should have an id$", () -> {
            assertThat(aCase.getId()).isNotNull();
        });
    }

}
