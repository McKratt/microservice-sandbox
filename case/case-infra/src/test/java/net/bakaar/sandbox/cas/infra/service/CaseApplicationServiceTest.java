package net.bakaar.sandbox.cas.infra.service;

import net.bakaar.sandbox.cas.domain.CreateCaseUseCase;
import net.bakaar.sandbox.cas.domain.command.CreateCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CaseApplicationServiceTest {

    @Test
    public void createCase_should_call_domaineService() {
        //Given
        CreateCaseUseCase domainService = mock(CreateCaseUseCase.class);
        CaseApplicationService service = new CaseApplicationService(domainService);
        PNumber pNumber = PNumber.of(98765432);
        CreateCaseCommand command = new CreateCaseCommand(pNumber);
        Case expectedCase = mock(Case.class);
        given(domainService.createCase(command)).willReturn(expectedCase);
        //When
        Case returnedCase = service.createCase(command);
        //Then
        assertThat(returnedCase).isSameAs(expectedCase);
        verify(domainService).createCase(command);
    }
}