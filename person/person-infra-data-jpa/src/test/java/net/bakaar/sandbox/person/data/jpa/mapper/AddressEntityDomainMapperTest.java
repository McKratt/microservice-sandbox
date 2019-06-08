package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.person.data.jpa.repository.AddressJpaRepository;
import net.bakaar.sandbox.person.domain.entity.Address;
import net.bakaar.sandbox.person.domain.vo.AddressNumber;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddressEntityDomainMapperTest {

    @Test
    public void mapToEntity_should_search_if_an_identical_address_exists_already() {
        //Given
        AddressJpaRepository addressJpaRepository = mock(AddressJpaRepository.class);
        AddressEntity existingAddress = new AddressEntity();
        String addressLine = "My Address";
        given(addressJpaRepository.findByAddressLine(addressLine)).willReturn(Optional.of(existingAddress));
        long addressId = 7685945362L;
        Address addressInput = Address.of(AddressNumber.of(addressId), addressLine);
        AddressEntityDomainMapper mapper = new AddressEntityDomainMapper(addressJpaRepository);
        //When
        AddressEntity entity = mapper.mapToEntity(addressInput);
        //Then
        verify(addressJpaRepository).findByAddressLine(addressLine);
        assertThat(entity).isSameAs(existingAddress);
    }

    @Test
    public void mapToEntity_should_create_new_entity() {
        //Given
        AddressJpaRepository addressJpaRepository = mock(AddressJpaRepository.class);
        String addressLine = "My Address";
        given(addressJpaRepository.findByAddressLine(addressLine)).willReturn(Optional.empty());
        long addressId = 7685945362L;
        Address addressInput = Address.of(AddressNumber.of(addressId), addressLine);
        AddressEntityDomainMapper mapper = new AddressEntityDomainMapper(addressJpaRepository);
        //When
        AddressEntity entity = mapper.mapToEntity(addressInput);
        //Then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getNumber()).isEqualTo(addressId);
        assertThat(entity.getAddressLine()).isEqualTo(addressLine);
    }
}