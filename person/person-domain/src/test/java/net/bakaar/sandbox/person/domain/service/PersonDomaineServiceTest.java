package net.bakaar.sandbox.person.domain.service;

import net.bakaar.sandbox.event.domain.EventStore;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.event.PartnerCreated;
import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonDomaineServiceTest {

    @Test
    public void createPartner_should_create_and_call_number_service() {
        //Given
        PartnerRepository partnerRepository = mock(PartnerRepository.class);
        given(partnerRepository.push(ArgumentMatchers.any(Partner.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        BusinessNumberRepository businessNumberRepository = mock(BusinessNumberRepository.class);
        PNumber pNumber = PNumber.of(12345678L);
        given(businessNumberRepository.createPartnerNumber()).willReturn(pNumber);
        EventStore eventStore = mock(EventStore.class);
        CreatePartnerUseCase service = new PersonDomaineService(partnerRepository, businessNumberRepository, eventStore);
        //When
        Partner createdPartner = service.createPartner("Einstein", "Albert", LocalDate.of(1879, 3, 14));
        //Then
        assertThat(createdPartner).isNotNull();
        assertThat(createdPartner.getId()).isSameAs(pNumber);
        verify(partnerRepository).push(ArgumentMatchers.any(Partner.class));
        verify(businessNumberRepository).createPartnerNumber();
        ArgumentCaptor<PartnerCreated> capturedEvent = ArgumentCaptor.forClass(PartnerCreated.class);
        verify(eventStore).store(capturedEvent.capture());
        PartnerCreated event = capturedEvent.getValue();
        assertThat(event).isNotNull();
    }
}