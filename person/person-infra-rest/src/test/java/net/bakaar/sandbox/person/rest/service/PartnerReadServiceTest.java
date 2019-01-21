package net.bakaar.sandbox.person.rest.service;

import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PartnerReadServiceTest {

    @Test
    public void featchPartnerById_should_return_the_correct_partner() {
        //Given
        PartnerReadStore readRepository = mock(PartnerReadStore.class);
        PartnerDTO mockedDto = mock(PartnerDTO.class);
        PNumber id = PNumber.of(12345678L);
        given(readRepository.fetchPartnerById(any())).willReturn(mockedDto);
        PartnerReadService service = new PartnerReadService(readRepository);
        //When
        PartnerDTO returnedPartner = service.fetchPartnerById(id);
        //Then
        assertThat(returnedPartner).isNotNull().isSameAs(mockedDto);
        verify(readRepository).fetchPartnerById(id);
    }

}