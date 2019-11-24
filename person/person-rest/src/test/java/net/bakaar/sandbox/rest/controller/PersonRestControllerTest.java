package net.bakaar.sandbox.rest.controller;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.rest.dto.CreatePersonCommandDTO;
import net.bakaar.sandbox.rest.dto.PersonDTO;
import net.bakaar.sandbox.rest.dto.PersonalAddressDTO;
import net.bakaar.sandbox.rest.mapper.PersonDtoMapper;
import net.bakaar.sandbox.rest.mapper.PersonalAddressDtoMapper;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonRestControllerTest {

    private final PersonApplicationService service = mock(PersonApplicationService.class);
    private final PersonDtoMapper mapper = mock(PersonDtoMapper.class);
    private final PersonalAddressDtoMapper personalAddressDtoMapper = mock(PersonalAddressDtoMapper.class);
    private final PersonRestController controller = new PersonRestController(service, mapper, personalAddressDtoMapper);
    private final Person returnedPerson = mock(Person.class);
    private final PersonDTO expectedDto = mock(PersonDTO.class);

    @Test
    void create_should_call_service() {
        //Given
        given(service.createPerson(any(CreatePersonCommand.class))).willReturn(returnedPerson);
        given(mapper.mapToDto(returnedPerson)).willReturn(expectedDto);
        CreatePersonCommandDTO input = new CreatePersonCommandDTO();
        String name = "MyName";
        String forename = "MyForename";
        LocalDate birthDate = LocalDate.now();
        input.setName(name);
        input.setForename(forename);
        input.setBirthDate(birthDate);
        PersonalAddressDTO mainAddressDto = mock(PersonalAddressDTO.class);
        input.setMainAddress(mainAddressDto);
        PersonalAddress mainAddress = mock(PersonalAddress.class);
        given(personalAddressDtoMapper.mapToDomain(mainAddressDto)).willReturn(mainAddress);
        //When
        ResponseEntity<PersonDTO> response = controller.create(input);
        //Then
        ArgumentCaptor<CreatePersonCommand> commandCaptor = ArgumentCaptor.forClass(CreatePersonCommand.class);
        verify(service).createPerson(commandCaptor.capture());
        CreatePersonCommand capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand).isNotNull();
        assertThat(capturedCommand.getName()).isEqualTo(name);
        assertThat(capturedCommand.getForename()).isEqualTo(forename);
        assertThat(capturedCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(capturedCommand.getMainAddress()).isSameAs(mainAddress);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(mapper).mapToDto(returnedPerson);
        assertThat(response.getBody()).isSameAs(expectedDto);
    }

    @Test
    void fetchAPartnerById_should_return_the_correct_partner() {
        //Given
        long id = 45678909L;
        PNumber pNumber = PNumber.of(id);
        given(service.readPerson(any())).willReturn(returnedPerson);
        given(mapper.mapToDto(returnedPerson)).willReturn(expectedDto);
        //When
        PersonDTO dto = controller.readPerson("P" + id);
        //Then
        assertThat(dto).isNotNull().isSameAs(expectedDto);
        ArgumentCaptor<PNumber> numberCaptor = ArgumentCaptor.forClass(PNumber.class);
        verify(service).readPerson(numberCaptor.capture());
        PNumber capturedNumber = numberCaptor.getValue();
        assertThat(capturedNumber).isEqualTo(pNumber);
    }
}
