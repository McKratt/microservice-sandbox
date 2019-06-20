package net.bakaar.sandbox.domain.shared;

import net.bakaar.sandbox.shared.domain.vo.BusinessNumber;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class AddressNumberTest {


    @Test
    public void of_should_create_a_new_AddressNumber() {
        //Given
        String pnummer = "A123456789";
        //When
        AddressNumber created = AddressNumber.of(pnummer);
        //Then
        assertThat(created).isNotNull();
    }

    @Test
    public void should_only_store_number() {
        //Given
        String pnummer = "A123456789";
        //When
        AddressNumber created = AddressNumber.of(pnummer);
        //Then
        assertThat((Long) getField(created, "value")).isEqualTo(123456789);
    }

    @Test
    public void of_should_throw_exception_arg_null() {
        //Given
        //When
        Throwable thrown = Assertions.catchThrowable(() -> AddressNumber.of(null));
        //Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).contains("null");
    }

    @Test
    public void of_should_throw_exception_if_pattern_wrong() {
        //Given
        //When
        Throwable thrown = Assertions.catchThrowable(() -> AddressNumber.of("jhd31"));
        //Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).contains("The input should follow the pattern A[0-9]{9}");
    }

    @Test
    public void format_should_return_in_good_pattern() {
        //Given
        AddressNumber pNumber = AddressNumber.of("A123456789");
        //When
        String formated = pNumber.format();
        //Then
        assertThat(formated).matches("A[0-9]{9}");
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(BusinessNumber.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void of_should_convert_to_string() {
        //Given
        long id = 12345678L;
        //When
        AddressNumber created = AddressNumber.of(id);
        //Then
        assertThat(created.format()).isEqualTo("A12345678");
    }

}