package net.bakaar.sandbox.rest.controller;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.rest.dto.CreatePartnerCommandDTO;
import net.bakaar.sandbox.rest.dto.PartnerDTO;
import net.bakaar.sandbox.rest.mapper.PartnerDomainDtoMapper;
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

public class PersonRestControllerTest {

    private final PersonApplicationService service = mock(PersonApplicationService.class);
    private final PartnerDomainDtoMapper mapper = mock(PartnerDomainDtoMapper.class);
    private final PartnerRestController controller = new PartnerRestController(service, mapper);
    private final Person returnedPerson = mock(Person.class);
    private final PartnerDTO expectedDto = mock(PartnerDTO.class);

    @Test
    public void create_should_call_service() {
        //Given
        given(service.createPartner(any(CreatePersonCommand.class))).willReturn(returnedPerson);
        given(mapper.mapToDto(returnedPerson)).willReturn(expectedDto);
        CreatePartnerCommandDTO input = new CreatePartnerCommandDTO();
        String name = "MyName";
        String forename = "MyForename";
        LocalDate birthDate = LocalDate.now();
        input.setName(name);
        input.setForename(forename);
        input.setBirthDate(birthDate);
        //When
        ResponseEntity<PartnerDTO> response = controller.create(input);
        //Then
        ArgumentCaptor<CreatePersonCommand> commandCaptor = ArgumentCaptor.forClass(CreatePersonCommand.class);
        verify(service).createPartner(commandCaptor.capture());
        CreatePersonCommand capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand).isNotNull();
        assertThat(capturedCommand.getName()).isEqualTo(name);
        assertThat(capturedCommand.getForename()).isEqualTo(forename);
        assertThat(capturedCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(mapper).mapToDto(returnedPerson);
        assertThat(response.getBody()).isSameAs(expectedDto);
    }

    @Test
    public void fetchAPartnerById_should_return_the_correct_partner() {
        //Given
        long id = 45678909L;
        PNumber pNumber = PNumber.of(id);
        given(service.readPartner(any())).willReturn(returnedPerson);
        given(mapper.mapToDto(returnedPerson)).willReturn(expectedDto);
        //When
        PartnerDTO dto = controller.readAPartner("P" + id);
        //Then
        assertThat(dto).isNotNull().isSameAs(expectedDto);
        ArgumentCaptor<PNumber> numberCaptor = ArgumentCaptor.forClass(PNumber.class);
        verify(service).readPartner(numberCaptor.capture());
        PNumber capturedNumber = numberCaptor.getValue();
        assertThat(capturedNumber).isEqualTo(pNumber);
    }
}
