package net.bakaar.sandbox.person.rest.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PersonRestServiceTest {

    private final CreatePartnerUseCase domainService = mock(PersonDomaineService.class);
    private final PartnerReadStore readRepository = mock(PartnerReadStore.class);
    private final PartnerDomainDtoMapper mapper = mock(PartnerDomainDtoMapper.class);
    private final PersonRestService service = new PersonRestService(domainService, readRepository, mapper);
    private final PartnerDTO mockedDto = mock(PartnerDTO.class);

    @Test
    public void createPartner_should_call_domain_service() {
        //Given
        Partner mockedPartner = mock(Partner.class);
        given(domainService.createPartner(any(CreatePartnerCommand.class))).willReturn(mockedPartner);
        given(mapper.mapToDto(mockedPartner)).willReturn(mockedDto);
        PartnerDTO input = new PartnerDTO();
        LocalDate birthDate = LocalDate.of(1981, 12, 16);
        input.setBirthDate(birthDate);
        String forename = "forename";
        input.setForename(forename);
        String name = "name";
        input.setName(name);
        //When
        PartnerDTO dto = service.createPartner(input);
        //Then
        ArgumentCaptor<CreatePartnerCommand> commandCaptured = ArgumentCaptor.forClass(CreatePartnerCommand.class);
        verify(domainService).createPartner(commandCaptured.capture());
        CreatePartnerCommand command = commandCaptured.getValue();
        assertThat(command.getBirthDate()).isSameAs(birthDate);
        assertThat(command.getForename()).isEqualTo(forename);
        assertThat(command.getName()).isEqualTo(name);
        verify(mapper).mapToDto(mockedPartner);
        verify(readRepository, times(0)).fetchPartnerById(any());
        assertThat(dto).isNotNull().isSameAs(mockedDto);
    }

    @Test
    public void featchPartnerById_should_return_the_correct_partner() {
        //Given
        PNumber id = PNumber.of(12345678L);
        given(readRepository.fetchPartnerById(any())).willReturn(mockedDto);
        //When
        PartnerDTO returnedPartner = service.fetchPartnerById(id);
        //Then
        assertThat(returnedPartner).isNotNull().isSameAs(mockedDto);
        verify(readRepository).fetchPartnerById(id);
    }
}