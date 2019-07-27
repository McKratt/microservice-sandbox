package net.bakaar.sandbox.domain.address;

import net.bakaar.sandbox.domain.shared.AddressNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

public class AddressTest {

    @Test
    public void secondaryOf_should_create_a_secondary_address() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        String address = "My AddressNumber";
        //When
        Address createdAddress = Address.of(id, address);
        //Then
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getAddress()).isSameAs(address);
        assertThat(createdAddress.getId()).isSameAs(id);
    }

    @Test
    public void mainOf_should_create_a_main_address() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        String address = "My AddressNumber";
        //When
        Address createdAddress = Address.of(id, address);
        //Then
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getAddress()).isSameAs(address);
        assertThat(createdAddress.getId()).isSameAs(id);
    }


    @Test
    public void constructor_should_throw_error_if_address_empty() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        //When
        Throwable thrown = catchThrowable(() -> Address.of(id, ""));
        //Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("address");
    }
}