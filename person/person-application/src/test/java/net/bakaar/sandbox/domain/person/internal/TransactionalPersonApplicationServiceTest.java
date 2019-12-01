package net.bakaar.sandbox.domain.person.internal;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TransactionalPersonApplicationServiceTest {

    private final Person mockedPerson = mock(Person.class);
    private PersonRepository repository = mock(PersonRepository.class);
    private PersonFactory factory = mock(PersonFactory.class);
    private PersonApplicationService service = new TransactionalPersonApplicationService(repository, factory);

    @Test
    void createPerson_should_call_domain_service() {
        //Given
        CreatePersonCommand input = mock(CreatePersonCommand.class);
        given(factory.createPerson(input)).willReturn(mockedPerson);
        given(repository.putPerson(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));
        //When
        Person person = service.createPerson(input);
        //Then
        verify(factory).createPerson(input);
        assertThat(person).isNotNull().isSameAs(mockedPerson);
    }

    @Test
    void readPerson_should_call_the_repository() {
        //Given
        PNumber id = mock(PNumber.class);
        given(repository.fetchPersonById(id)).willReturn(mockedPerson);
        //When
        Person person = service.readPerson(id);
        //Then
        verify(repository).fetchPersonById(id);
        assertThat(person).isSameAs(mockedPerson);
    }
}
