package net.bakaar.sandbox.person.rest.controller;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PartnerRestControllerTest {

    private final CreatePartnerUseCase service = mock(CreatePartnerUseCase.class);
    private final PartnerDomainDtoMapper mapper = mock(PartnerDomainDtoMapper.class);
    private final PartnerReadStore readStore = mock(PartnerReadStore.class);
    private final PartnerRestController controller = new PartnerRestController(service, readStore, mapper);

    @Test
    public void create_should_call_service() {
        Partner returnedPartner = mock(Partner.class);
        //Given
        given(service.createPartner(any(CreatePartnerCommand.class))).willReturn(returnedPartner);
        PartnerDTO expectedDto = mock(PartnerDTO.class);
        given(mapper.mapToDto(returnedPartner)).willReturn(expectedDto);
        PartnerDTO input = new PartnerDTO();
        String name = "MyName";
        String forename = "MyForename";
        LocalDate birthDate = LocalDate.now();
        input.setName(name);
        input.setForename(forename);
        input.setBirthDate(birthDate);
        //When
        ResponseEntity<PartnerDTO> response = controller.create(input);
        //Then
        ArgumentCaptor<CreatePartnerCommand> commandCaptor = ArgumentCaptor.forClass(CreatePartnerCommand.class);
        verify(service).createPartner(commandCaptor.capture());
        CreatePartnerCommand capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand).isNotNull();
        assertThat(capturedCommand.getName()).isEqualTo(name);
        assertThat(capturedCommand.getForename()).isEqualTo(forename);
        assertThat(capturedCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(mapper).mapToDto(returnedPartner);
        assertThat(response.getBody()).isSameAs(expectedDto);
    }

    @Test
    public void fetchAPartnerById_should_return_the_correct_partner() {
        //Given
        final PartnerDTO returnedPartner = mock(PartnerDTO.class);
        long id = 45678909L;
        PNumber pNumber = PNumber.of(id);
        given(readStore.fetchPartnerById(pNumber)).willReturn(returnedPartner);
        //When
        PartnerDTO dto = controller.readAPartner("P" + id);
        //Then
        assertThat(dto).isNotNull().isSameAs(returnedPartner);
    }
}
