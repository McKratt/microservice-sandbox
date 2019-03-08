package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.PartnerFactory;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonApplicationServiceTest {

    private final Partner mockedPartner = mock(Partner.class);
    private PartnerRepository repository = mock(PartnerRepository.class);
    private PartnerFactory factory = mock(PartnerFactory.class);
    private PersonApplicationService service = new PersonApplicationService(repository, factory);

    @Test
    public void createPartner_should_call_domain_service() {
        //Given
        CreatePartnerCommand input = mock(CreatePartnerCommand.class);
        given(factory.createPartner(input)).willReturn(mockedPartner);
        //When
        Partner partner = service.createPartner(input);
        //Then
        verify(factory).createPartner(input);
        assertThat(partner).isNotNull().isSameAs(mockedPartner);
    }

//    @Test
//    public void readPartner_should_call_the_repository() {
//        //Given
//        ReadPartnerQuery query = mock(ReadPartnerQuery.class);
//        given(repository.fetchPartner(query)).willReturn(mockedPartner);
//        //When
//        Partner partner = service.readPartner(query);
//        //Then
//        verify(repository).fetchPartner(query);
//        assertThat(partner).isSameAs(mockedPartner);
//    }
}
