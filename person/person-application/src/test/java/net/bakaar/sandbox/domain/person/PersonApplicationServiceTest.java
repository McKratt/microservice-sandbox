package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PersonApplicationServiceTest {

    private final Person mockedPerson = mock(Person.class);
    private PersonRepository repository = mock(PersonRepository.class);
    private BusinessNumberRepository idRepository = mock(BusinessNumberRepository.class);
    private PartnerFactory factory = mock(PartnerFactory.class);
    private PersonApplicationService service = new PersonApplicationService(repository, factory, idRepository);

    @Test
    public void createPartner_should_call_domain_service() {
        //Given
        CreatePersonCommand input = mock(CreatePersonCommand.class);
        given(factory.createPartner(input)).willReturn(mockedPerson);
        given(repository.putPartner(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));
        //When
        Person person = service.createPartner(input);
        //Then
        verify(factory).createPartner(input);
        assertThat(person).isNotNull().isSameAs(mockedPerson);
    }

    @Test
    public void readPartner_should_call_the_repository() {
        //Given
        PNumber id = mock(PNumber.class);
        given(repository.fetchPartnerById(id)).willReturn(mockedPerson);
        //When
        Person person = service.readPartner(id);
        //Then
        verify(repository).fetchPartnerById(id);
        assertThat(person).isSameAs(mockedPerson);
    }

    @Test
    public void searchPartner_should_not_search_in_repository() {
        //Given
        SearchPartnerCommand command = new SearchPartnerCommand();
        String id = "P12345678";
        command.setPNumber(id);
        given(repository.fetchPartnerById(any(PNumber.class))).willReturn(mockedPerson);
        //When
        List<Person> returnedList = service.searchPartners(command);
        //Then
        assertThat(returnedList).isNotEmpty().containsOnly(mockedPerson);
        ArgumentCaptor<PNumber> numberCaptor = ArgumentCaptor.forClass(PNumber.class);
        verify(repository).fetchPartnerById(numberCaptor.capture());
        verify(repository, never()).searchPartner(any());
        PNumber numberCaptured = numberCaptor.getValue();
        assertThat(numberCaptured.format()).isEqualTo(id);

    }

    @Test
    public void searchPartner_should_only_search_in_repository() {
        //Given
        SearchPartnerCommand command = new SearchPartnerCommand();
        String name = "myName";
        command.setName(name);
        String socialNumber = "75698416354632";
        command.setSocialNumber(socialNumber);
        given(repository.searchPartner(any(SearchPersonQuery.class))).willReturn(Collections.singletonList(mockedPerson));
        //When
        List<Person> returnedList = service.searchPartners(command);
        //Then
        assertThat(returnedList).isNotEmpty().containsOnly(mockedPerson);
        ArgumentCaptor<SearchPersonQuery> queryCaptor = ArgumentCaptor.forClass(SearchPersonQuery.class);
        verify(repository).searchPartner(queryCaptor.capture());
        verify(repository, never()).fetchPartnerById(any());
        SearchPersonQuery queryCaptured = queryCaptor.getValue();
        assertThat(queryCaptured.getName()).isEqualTo(name);
        assertThat(queryCaptured.getSocialSocialNumber()).isEqualTo(socialNumber);
    }

    @Test
    public void searchPartner_should_concatenate_results() {
        //Given
        SearchPartnerCommand command = new SearchPartnerCommand();
        String name = "myName";
        command.setName(name);
        String socialNumber = "75698416354632";
        command.setSocialNumber(socialNumber);
        given(repository.searchPartner(any(SearchPersonQuery.class))).willReturn(Collections.singletonList(mockedPerson));
        String id = "P12345678";
        command.setPNumber(id);
        Person mockedPerson2 = mock(Person.class);
        given(repository.fetchPartnerById(any(PNumber.class))).willReturn(mockedPerson2);
        //When
        List<Person> returnedList = service.searchPartners(command);
        //Then
        assertThat(returnedList).isNotEmpty().containsOnly(mockedPerson, mockedPerson2);
        ArgumentCaptor<PNumber> numberCaptor = ArgumentCaptor.forClass(PNumber.class);
        verify(repository).fetchPartnerById(numberCaptor.capture());
        PNumber numberCaptured = numberCaptor.getValue();
        assertThat(numberCaptured.format()).isEqualTo(id);
        ArgumentCaptor<SearchPersonQuery> queryCaptor = ArgumentCaptor.forClass(SearchPersonQuery.class);
        verify(repository).searchPartner(queryCaptor.capture());
        SearchPersonQuery queryCaptured = queryCaptor.getValue();
        assertThat(queryCaptured.getName()).isEqualTo(name);
        assertThat(queryCaptured.getSocialSocialNumber()).isEqualTo(socialNumber);
    }
}
