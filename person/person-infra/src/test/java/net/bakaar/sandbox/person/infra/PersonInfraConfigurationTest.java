package net.bakaar.sandbox.person.infra;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.domain.service.PersonDomaineService;
import net.bakaar.sandbox.person.infra.service.PersonApplicationService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class PersonInfraConfigurationTest {

    @Test
    public void createPartnerApplicationService_should_instantiate_domainService() {
        //Given
        PersonInfraConfiguration configuration = new PersonInfraConfiguration();
        PartnerRepository partnerRepository = mock(PartnerRepository.class);
        BusinessNumberRepository numberRepository = mock(BusinessNumberRepository.class);
        //When
        CreatePartnerUseCase returnedService = configuration.createPartnerApplicationService(partnerRepository, numberRepository);
        //Then
        assertThat(returnedService).isInstanceOf(PersonApplicationService.class);
        PersonDomaineService domainService = (PersonDomaineService) getField(returnedService, "domainService");
        assertThat(domainService).isNotNull();
        assertThat(getField(domainService, "store")).isSameAs(partnerRepository);
        assertThat(getField(domainService, "businessNumberRepository")).isSameAs(numberRepository);
    }
}