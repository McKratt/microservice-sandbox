package net.bakaar.sandbox.infra.data.jpa.mapper;

import net.bakaar.sandbox.domain.address.Address;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.infra.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.infra.data.jpa.repository.AddressJpaRepository;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AddressNumberEntityDomainMapperTest {

    //    private AddressJpaRepository addressJpaRepository = mock(AddressJpaRepository.class);
//    private AddressEntityDomainMapper mapper = new AddressEntityDomainMapper(addressJpaRepository);
    private AddressEntityDomainMapper mapper = new AddressEntityDomainMapper();

//    @Test
//    public void mapToEntity_should_search_if_an_identical_address_exists_already() {
//        //Given
//        AddressEntity existingAddress = mock(AddressEntity.class);
//        String addressLine = "My AddressNumber";
//        given(addressJpaRepository.findByAddressLine(addressLine)).willReturn(Optional.of(existingAddress));
//        long addressId = 7685945362L;
//        AddressNumber addressInput = AddressNumber.of(AddressNumber.of(addressId), addressLine);
//        //When
//        AddressEntity entity = mapper.mapToEntity(addressInput);
//        //Then
//        verify(addressJpaRepository).findByAddressLine(addressLine);
//        assertThat(entity).isSameAs(existingAddress);
//    }
//
//    @Test
//    public void mapToEntity_should_use_the_existing_address_if_number_is_set() {
//        // Given
//        AddressEntity existingAddress = mock(AddressEntity.class);
//        long number = 756473812L;
//        given(addressJpaRepository.findByNumber(number)).willReturn(Optional.of(existingAddress));
//        AddressNumber addressInput = AddressNumber.of(AddressNumber.of(number), "My AddressNumber");
//        // When
//        AddressEntity entity = mapper.mapToEntity(addressInput);
//        // Then
//        verify(addressJpaRepository).findByNumber(number);
//        assertThat(entity).isSameAs(existingAddress);
//    }

    @Test
    public void mapToEntity_should_create_new_entity() {
        //Given
        AddressJpaRepository addressJpaRepository = mock(AddressJpaRepository.class);
        String addressLine = "My AddressNumber";
        given(addressJpaRepository.findByAddressLine(addressLine)).willReturn(Optional.empty());
        long addressId = 7685945362L;
        Address addressInput = Address.of(AddressNumber.of(addressId), addressLine);
        //When
        AddressEntity entity = mapper.mapToEntity(addressInput);
        //Then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getNumber()).isEqualTo(addressId);
        assertThat(entity.getAddressLine()).isEqualTo(addressLine);
    }

    @Test
    public void mapToDomain_should_map_correctly() {
        // Given
        String addressLine = "My AddressNumber";
        AddressEntity entity = new AddressEntity();
        entity.setAddressLine(addressLine);
        entity.setNumber(123456789);
        PersonAddressesEntity link = new PersonAddressesEntity();
        link.setAddress(entity);
        link.setMain(true);
        // When
        Address domain = mapper.mapToDomain(link);
        // Then
        assertThat(domain).isNotNull();
        assertThat(domain.getAddress()).isEqualTo(addressLine);
        assertThat(domain.isMain()).isTrue();
    }
}