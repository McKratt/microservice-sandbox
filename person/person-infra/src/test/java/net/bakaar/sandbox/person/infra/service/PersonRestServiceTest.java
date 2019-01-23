package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonRestServiceTest {

    @Test
    public void createPartner_should_call_domain_service() {
        //Given
        CreatePartnerUseCase domainService = mock(PersonDomaineService.class);
        PersonRestService service = new PersonRestService(domainService);
        Partner mockedPartner = mock(Partner.class);
        given(domainService.createPartner(any(CreatePartnerCommand.class))).willReturn(mockedPartner);

        CreatePartnerCommand input = mock(CreatePartnerCommand.class);
        //When
        Partner partner = service.createPartner(input);
        //Then
        verify(domainService).createPartner(input);
        assertThat(partner).isNotNull().isSameAs(mockedPartner);
    }
}
