package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.command.OpenCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.domain.event.CaseCreated;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import net.bakaar.sandbox.cas.domain.repository.CaseRepository;
import net.bakaar.sandbox.event.domain.DomainEventEmitter;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CaseServiceTest {

    private DomainEventEmitter emitter = mock(DomainEventEmitter.class);
    private CaseRepository repository = mock(CaseRepository.class);
    private BusinessIdRepository businessIdRepository = mock(BusinessIdRepository.class);
    private CaseService service = new CaseService(emitter, repository, businessIdRepository);
    private final PNumber pNumber = PNumber.of(12345678);

    @Test
    public void createCase_should_return_a_case() {
        // Given
        given(repository.save(any(Case.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        given(businessIdRepository.generateId()).willReturn(UUID.randomUUID().toString());
        // When
        Case aCase = service.openCase(new OpenCaseCommand(pNumber));
        // Then
        assertThat(aCase).isNotNull();
        assertThat(aCase.getInjured()).isSameAs(pNumber);
        verify(repository).save(aCase);
    }

    @Test
    public void createCase_should_emit_an_event() {
        // Given
        given(repository.save(any(Case.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        given(businessIdRepository.generateId()).willReturn(UUID.randomUUID().toString());
        // When
        Case aCase = service.openCase(new OpenCaseCommand(pNumber));
        // Then
        ArgumentCaptor<CaseCreated> captor = ArgumentCaptor.forClass(CaseCreated.class);
        verify(emitter).emit(captor.capture());
        CaseCreated eventEmitted = captor.getValue();
        assertThat(eventEmitted.getId()).isEqualTo(aCase.getId());
        assertThat(eventEmitted.getPNumber()).isEqualTo(aCase.getInjured());

    }
}