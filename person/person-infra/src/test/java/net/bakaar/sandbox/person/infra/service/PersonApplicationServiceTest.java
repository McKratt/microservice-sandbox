package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Address;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.AddressNumber;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.vo.SearchPartnerQuery;
import net.bakaar.sandbox.person.infra.service.vo.CreateAddressCommand;
import net.bakaar.sandbox.person.infra.service.vo.SearchPartnerCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PersonApplicationServiceTest {

    private final Partner mockedPartner = mock(Partner.class);
    private PartnerRepository repository = mock(PartnerRepository.class);
    private BusinessNumberRepository idRepository = mock(BusinessNumberRepository.class);
    private PartnerFactory factory = mock(PartnerFactory.class);
    private PersonApplicationService service = new PersonApplicationService(repository, factory, idRepository);

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

    @Test
    public void addAddressToPartner_should_add_the_address_to_the_corresponding_partner() {
        //Given
        PNumber concernedPartnerId = PNumber.of(76452174);
        CreateAddressCommand command = CreateAddressCommand.of("Blabla Address");
        given(repository.fetchPartnerById(concernedPartnerId)).willReturn(mockedPartner);
        given(idRepository.fetchNextAddressNumber()).willReturn(mock(AddressNumber.class));
        given(repository.putPartner(any())).willAnswer(invocation -> invocation.getArgument(0));
        //When
        Partner returnedPartner = service.addAddressToPartner(concernedPartnerId, command);
        //Then
        verify(repository).fetchPartnerById(concernedPartnerId);
        assertThat(returnedPartner).isSameAs(mockedPartner);
        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(idRepository).fetchNextAddressNumber();
        verify(mockedPartner).addNewAddress(addressCaptor.capture());
        Address capturedAddress = addressCaptor.getValue();
        assertThat(capturedAddress).isNotNull();
        assertThat(capturedAddress.isMain()).isEqualTo(command.isMain());
        assertThat(capturedAddress.getAddress()).isEqualTo(command.getAddress());
    }

    @Test
    public void addAddressToPartner_should_throw_error_if_partner_not_exists() {
        //Given
        PNumber concernedPartnerId = PNumber.of(76452174);
        CreateAddressCommand command = CreateAddressCommand.of("Blabla Address");
        given(repository.fetchPartnerById(any())).willReturn(null);
        //When
        Throwable thrown = catchThrowable(() -> service.addAddressToPartner(concernedPartnerId, command));
        //Then
        assertThat(thrown).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).contains("partner", concernedPartnerId.format());
    }
}
