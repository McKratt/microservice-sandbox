package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PersonFactoryTest {

    private BusinessNumberRepository idRepository = mock(BusinessNumberRepository.class);
    private PersonFactory factory = new PersonFactory(idRepository);

    @Test
    public void createPerson_should_call_BusinessNumberRepository_and_return_a_person_containing_command_fields() {
        //Given
        given(idRepository.fetchNextPNumber()).willReturn(mock(PNumber.class));
        PersonalAddress mainAddress = mock(PersonalAddress.class);
        CreatePersonCommand command = CreatePersonCommand
                .of("name", "forename", LocalDate.now().minus(1, ChronoUnit.YEARS), mainAddress);
        //When
        Person createdPerson = factory.createPerson(command);
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getName().getLine()).isEqualTo(command.getName());
        assertThat(createdPerson.getForename().getLine()).isEqualTo(command.getForename());
        assertThat(createdPerson.getBirthDate()).isEqualTo(command.getBirthDate());
        assertThat(createdPerson.getId()).isNotNull();
        assertThat(createdPerson.getMainAddress()).isSameAs(mainAddress);
    }
}
