package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.shared.AddressNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

public class AddressNumberTest {

    @Test
    public void secondaryOf_should_create_a_secondary_address() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        String address = "My AddressNumber";
        //When
        net.bakaar.sandbox.domain.person.PersonalAddress createdPersonalAddress = net.bakaar.sandbox.domain.person.PersonalAddress.of(id, address);
        //Then
        assertThat(createdPersonalAddress).isNotNull();
        assertThat(createdPersonalAddress.getAddress()).isSameAs(address);
        assertThat(createdPersonalAddress.getId()).isSameAs(id);
    }

    @Test
    public void mainOf_should_create_a_main_address() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        String address = "My AddressNumber";
        //When
        net.bakaar.sandbox.domain.person.PersonalAddress createdPersonalAddress = net.bakaar.sandbox.domain.person.PersonalAddress.of(id, address);
        //Then
        assertThat(createdPersonalAddress).isNotNull();
        assertThat(createdPersonalAddress.getAddress()).isSameAs(address);
        assertThat(createdPersonalAddress.getId()).isSameAs(id);
    }


    @Test
    public void constructor_should_throw_error_if_address_empty() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        //When
        Throwable thrown = catchThrowable(() -> net.bakaar.sandbox.domain.person.PersonalAddress.of(id, ""));
        //Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("address");
    }
}