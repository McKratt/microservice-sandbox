package net.bakaar.sandbox.domain.person.infra;

import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.infra.service.BusinessNumberRepository;
import net.bakaar.sandbox.domain.person.infra.service.PartnerFactory;
import net.bakaar.sandbox.domain.person.infra.service.PersonApplicationService;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class PersonInfraConfigurationTest {

    @Test
    public void createPartnerApplicationService_should_instantiate_domainService() {
        //Given
        PersonInfraConfiguration configuration = new PersonInfraConfiguration();
        PersonRepository personRepository = mock(PersonRepository.class);
        given(personRepository.putPartner(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));
        BusinessNumberRepository numberRepository = mock(BusinessNumberRepository.class);
        given(numberRepository.fetchNextPNumber()).willReturn(PNumber.of(12345678));

        //When
        PersonApplicationService returnedService = configuration.createPartnerApplicationService(personRepository, numberRepository);
        //Then
        assertThat(returnedService).isInstanceOf(PersonApplicationService.class);
        assertThat(getField(returnedService, "personRepository")).isSameAs(personRepository);
        PartnerFactory factory = (PartnerFactory) getField(returnedService, "partnerFactory");
        assertThat(factory).isNotNull().isInstanceOf(PartnerFactory.class);
        assertThat(getField(factory, "businessNumberRepository")).isNotNull().isSameAs(numberRepository);
    }
}