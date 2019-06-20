package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.person.address.AddressRepository;
import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PersonFactoryTest {

    private BusinessNumberRepository idRepository = mock(BusinessNumberRepository.class);
    private AddressRepository addressRepository = mock(AddressRepository.class);
    private PartnerFactory factory = new PartnerFactory(idRepository, addressRepository);

    @Test
    public void createPartner_should_call_BusinessNumberRepository_and_return_a_partner_containing_command_fields() {
        //Given
        given(idRepository.fetchNextPNumber()).willReturn(mock(PNumber.class));
        AddressNumber addressNumber = mock(AddressNumber.class);
        net.bakaar.sandbox.domain.person.PersonalAddress mockedPersonalAddress = mock(net.bakaar.sandbox.domain.person.PersonalAddress.class);
        given(addressRepository.fetchAddress(addressNumber)).willReturn(mockedPersonalAddress);
        CreatePersonCommand command = CreatePersonCommand.of("name", "forename", LocalDate.now().minus(1, ChronoUnit.YEARS), addressNumber);
        //When
        Person createdPerson = factory.createPartner(command);
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getName().getLine()).isEqualTo(command.getName());
        assertThat(createdPerson.getForename().getLine()).isEqualTo(command.getForename());
        assertThat(createdPerson.getBirthDate()).isEqualTo(command.getBirthDate());
        assertThat(createdPerson.getId()).isNotNull();
        assertThat(createdPerson.getMainPersonalAddress()).isSameAs(mockedPersonalAddress);
    }
}