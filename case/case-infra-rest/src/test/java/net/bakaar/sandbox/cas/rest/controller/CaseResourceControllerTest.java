package net.bakaar.sandbox.cas.rest.controller;

import net.bakaar.sandbox.cas.domain.command.CreateCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.infra.service.CaseApplicationService;
import net.bakaar.sandbox.cas.rest.dto.CaseDTO;
import net.bakaar.sandbox.cas.rest.dto.CreateCaseCommandDTO;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CaseResourceControllerTest {

    private CaseApplicationService service = mock(CaseApplicationService.class);
    private CaseResourceController controler = new CaseResourceController(service);

    @Test
    public void addNewCase_should_call_domainService() {
        //Given
        String pNumber = "P12345678";
        CreateCaseCommandDTO dto = new CreateCaseCommandDTO();
        dto.setInsuredNumber(pNumber);
        LocalDate birthDate = LocalDate.of(1981, 12, 16);
        given(service.createCase(any())).willReturn(Case.builder()
                .withBusinnessId(UUID.randomUUID().toString())
                .withInjured(PNumber.of(pNumber)));
        //When
        ResponseEntity<CaseDTO> responseEntity = controler.addNewCase(dto);
        //Then
        ArgumentCaptor<CreateCaseCommand> commandCaptor = ArgumentCaptor.forClass(CreateCaseCommand.class);
        verify(service).createCase(commandCaptor.capture());
        CreateCaseCommand command = commandCaptor.getValue();
        assertThat(command).isNotNull();
        assertThat(command.getInsured().format()).isEqualTo(pNumber);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull().isInstanceOf(CaseDTO.class);
    }
}