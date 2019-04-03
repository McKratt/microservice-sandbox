package net.bakaar.sandbox.person.domain.event;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerModifiedTest {

    @Test
    public void of_should_initialize_object_correctly() {
        //Given
        PNumber pNumber = PNumber.of(12345678L);
        //When
        PartnerModified event = PartnerModified.of(pNumber);
        //Then
        assertThat(event.getPNumber()).isSameAs(pNumber);
    }
}