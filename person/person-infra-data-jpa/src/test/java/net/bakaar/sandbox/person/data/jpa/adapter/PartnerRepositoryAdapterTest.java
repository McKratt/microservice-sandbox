package net.bakaar.sandbox.person.data.jpa.adapter;

import net.bakaar.sandbox.person.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDTOMapper;
import net.bakaar.sandbox.person.data.jpa.mapper.PartnerEntityDomainMapper;
import net.bakaar.sandbox.person.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PartnerRepositoryAdapterTest {

    private final PartnerEntityDomainMapper entityDomainMapper = mock(PartnerEntityDomainMapper.class);
    private final PartnerEntityDTOMapper entityDTOMapper = mock(PartnerEntityDTOMapper.class);
    private final PersonJpaRepository repository = mock(PersonJpaRepository.class);
    private final PartnerRepositoryAdapter adapter = new PartnerRepositoryAdapter(repository, entityDomainMapper, entityDTOMapper);
    private final PersonEntity mockedEntity = mock(PersonEntity.class);

    @Test
    public void push_should_store_partner_in_db() {
        //Given
        Partner input = mock(Partner.class);
        Partner returned = mock(Partner.class);
        given(entityDomainMapper.mapToEntity(input)).willReturn(mockedEntity);
        given(repository.save(any(PersonEntity.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(entityDomainMapper.mapToDomain(mockedEntity)).willReturn(returned);
        //When
        Partner partner = adapter.push(input);
        //Then
        verify(entityDomainMapper).mapToEntity(input);
        verify(repository).save(mockedEntity);
        verify(entityDomainMapper).mapToDomain(mockedEntity);
        assertThat(partner).isNotNull().isSameAs(returned);
    }

    @Test
    public void fetchPartnerById_should_read_partner_from_db() {
        //Given
        long id = 12345678L;
        PNumber pNumber = PNumber.of(id);
        given(repository.findByPNumber(id)).willReturn(mockedEntity);
        PartnerDTO returnedDto = mock(PartnerDTO.class);
        given(entityDTOMapper.mapToDto(mockedEntity)).willReturn(returnedDto);
        //When
        PartnerDTO dto = adapter.fetchPartnerById(pNumber);
        //Then
        assertThat(dto).isNotNull().isSameAs(returnedDto);
        verify(repository).findByPNumber(id);
        verify(entityDTOMapper).mapToDto(mockedEntity);


    }
}