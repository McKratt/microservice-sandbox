package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.query.ReadPartnerQuery;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomainService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonApplicationServiceTest {

    private final Partner mockedPartner = mock(Partner.class);
    private CreatePartnerUseCase domainService = mock(PersonDomainService.class);
    private PartnerRepository repository = mock(PartnerRepository.class);
    private PersonApplicationService service = new PersonApplicationService(domainService, repository);

    @Test
    public void createPartner_should_call_domain_service() {
        //Given
        CreatePartnerCommand input = mock(CreatePartnerCommand.class);
        given(domainService.createPartner(input)).willReturn(mockedPartner);
        //When
        Partner partner = service.createPartner(input);
        //Then
        verify(domainService).createPartner(input);
        assertThat(partner).isNotNull().isSameAs(mockedPartner);
    }

    @Test
    public void readPartner_should_call_the_repository() {
        //Given
        ReadPartnerQuery query = mock(ReadPartnerQuery.class);
        given(repository.fetchPartner(query)).willReturn(mockedPartner);
        //When
        Partner partner = service.readPartner(query);
        //Then
        verify(repository).fetchPartner(query);
        assertThat(partner).isSameAs(mockedPartner);
    }
}
