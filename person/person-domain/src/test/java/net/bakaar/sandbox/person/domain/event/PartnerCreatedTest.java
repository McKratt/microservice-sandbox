package net.bakaar.sandbox.person.domain.event;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerCreatedTest {

    @Test
    public void of_should_initialize_object_correctly() {
        //Given
        PNumber pNumber = PNumber.of(12345678L);
        //When
        PartnerCreated event = PartnerCreated.of(pNumber);
        //Then
        assertThat(event.getPNumber()).isSameAs(pNumber);
    }
}