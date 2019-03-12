package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.PartnerFactory;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.vo.SearchPartnerQuery;
import net.bakaar.sandbox.person.infra.service.vo.SearchPartnerCommand;
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

    private final Partner mockedPartner = mock(Partner.class);
    private PartnerRepository repository = mock(PartnerRepository.class);
    private PartnerFactory factory = mock(PartnerFactory.class);
    private PersonApplicationService service = new PersonApplicationService(repository, factory);

    @Test
    public void createPartner_should_call_domain_service() {
        //Given
        CreatePartnerCommand input = mock(CreatePartnerCommand.class);
        given(factory.createPartner(input)).willReturn(mockedPartner);
        given(repository.putPartner(any(Partner.class))).willAnswer(invocation -> invocation.getArgument(0));
        //When
        Partner partner = service.createPartner(input);
        //Then
        verify(factory).createPartner(input);
        assertThat(partner).isNotNull().isSameAs(mockedPartner);
    }

    @Test
    public void readPartner_should_call_the_repository() {
        //Given
        PNumber id = mock(PNumber.class);
        given(repository.fetchPartnerById(id)).willReturn(mockedPartner);
        //When
        Partner partner = service.readPartner(id);
        //Then
        verify(repository).fetchPartnerById(id);
        assertThat(partner).isSameAs(mockedPartner);
    }

    @Test
    public void searchPartner_should_not_search_in_repository() {
        //Given
        SearchPartnerCommand command = new SearchPartnerCommand();
        String id = "P12345678";
        command.setPNumber(id);
        given(repository.fetchPartnerById(any(PNumber.class))).willReturn(mockedPartner);
        //When
        List<Partner> returnedList = service.searchPartners(command);
        //Then
        assertThat(returnedList).isNotEmpty().containsOnly(mockedPartner);
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
        given(repository.searchPartner(any(SearchPartnerQuery.class))).willReturn(Collections.singletonList(mockedPartner));
        //When
        List<Partner> returnedList = service.searchPartners(command);
        //Then
        assertThat(returnedList).isNotEmpty().containsOnly(mockedPartner);
        ArgumentCaptor<SearchPartnerQuery> queryCaptor = ArgumentCaptor.forClass(SearchPartnerQuery.class);
        verify(repository).searchPartner(queryCaptor.capture());
        verify(repository, never()).fetchPartnerById(any());
        SearchPartnerQuery queryCaptured = queryCaptor.getValue();
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
        given(repository.searchPartner(any(SearchPartnerQuery.class))).willReturn(Collections.singletonList(mockedPartner));
        String id = "P12345678";
        command.setPNumber(id);
        Partner mockedPartner2 = mock(Partner.class);
        given(repository.fetchPartnerById(any(PNumber.class))).willReturn(mockedPartner2);
        //When
        List<Partner> returnedList = service.searchPartners(command);
        //Then
        assertThat(returnedList).isNotEmpty().containsOnly(mockedPartner, mockedPartner2);
        ArgumentCaptor<PNumber> numberCaptor = ArgumentCaptor.forClass(PNumber.class);
        verify(repository).fetchPartnerById(numberCaptor.capture());
        PNumber numberCaptured = numberCaptor.getValue();
        assertThat(numberCaptured.format()).isEqualTo(id);
        ArgumentCaptor<SearchPartnerQuery> queryCaptor = ArgumentCaptor.forClass(SearchPartnerQuery.class);
        verify(repository).searchPartner(queryCaptor.capture());
        SearchPartnerQuery queryCaptured = queryCaptor.getValue();
        assertThat(queryCaptured.getName()).isEqualTo(name);
        assertThat(queryCaptured.getSocialSocialNumber()).isEqualTo(socialNumber);
    }
}
