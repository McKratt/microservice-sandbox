package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CaseTest {

    @Test
    public void builder_should_make_case() {
        //Given
        String id = "ID";
        PNumber pNumber = PNumber.of("P12345678");
        //When
        Case aCase = Case.builder().withBusinnessId(id).withInjured(pNumber);
        //Then
        assertThat(aCase).isNotNull();
        assertThat(aCase.getId()).isEqualTo(id);
        assertThat(aCase.getInjured()).isEqualTo(pNumber);
    }
}