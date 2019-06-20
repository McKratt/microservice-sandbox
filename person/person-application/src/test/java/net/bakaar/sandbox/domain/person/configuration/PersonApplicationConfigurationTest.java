package net.bakaar.sandbox.domain.person.configuration;

import net.bakaar.sandbox.domain.person.PartnerFactory;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.domain.person.address.AddressRepository;
import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class PersonApplicationConfigurationTest {

    @Test
    public void createPartnerApplicationService_should_instantiate_domainService() {
        //Given
        PersonApplicationConfiguration configuration = new PersonApplicationConfiguration();
        PersonRepository personRepository = mock(PersonRepository.class);
//        given(personRepository.putPartner(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));
        BusinessNumberRepository numberRepository = mock(BusinessNumberRepository.class);
//        given(numberRepository.fetchNextPNumber()).willReturn(PNumber.of(12345678));
        AddressRepository addressRepository = mock(AddressRepository.class);

        //When
        PersonApplicationService returnedService = configuration.createPartnerApplicationService(personRepository, numberRepository, addressRepository);
        //Then
        assertThat(returnedService).isInstanceOf(PersonApplicationService.class);
        assertThat(getField(returnedService, "personRepository")).isSameAs(personRepository);
        PartnerFactory factory = (PartnerFactory) getField(returnedService, "partnerFactory");
        assertThat(factory).isNotNull().isInstanceOf(PartnerFactory.class);
        assertThat(getField(factory, "businessNumberRepository")).isNotNull().isSameAs(numberRepository);
    }
}