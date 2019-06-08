package net.bakaar.sandbox.shared.domain.vo;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class PNumberTest {

    @Test
    public void of_should_create_a_new_PNummer() {
        //Given
        String pnummer = "P12345678";
        //When
        PNumber created = PNumber.of(pnummer);
        //Then
        assertThat(created).isNotNull();
    }

    @Test
    public void should_only_store_number() {
        //Given
        String pnummer = "P12345678";
        //When
        PNumber created = PNumber.of(pnummer);
        //Then
        assertThat((Long) getField(created, "value")).isEqualTo(12345678);
    }

    @Test
    public void of_should_throw_exception_arg_null() {
        //Given
        //When
        Throwable thrown = Assertions.catchThrowable(() -> PNumber.of(null));
        //Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).contains("null");
    }

    @Test
    public void of_should_throw_exception_if_pattern_wrong() {
        //Given
        //When
        Throwable thrown = Assertions.catchThrowable(() -> PNumber.of("jhd31"));
        //Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).contains("PNumber should follow the pattern P[0-9]{8}");
    }

    @Test
    public void format_should_return_in_good_pattern() {
        //Given
        PNumber pNumber = PNumber.of("P12345678");
        //When
        String formated = pNumber.format();
        //Then
        assertThat(formated).matches("P[0-9]{8}");
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(PNumber.class)
                .verify();
    }

    @Test
    public void of_should_convert_to_string() {
        //Given
        long id = 12345678L;
        //When
        PNumber created = PNumber.of(id);
        //Then
        assertThat(created.format()).isEqualTo("P12345678");
    }
}