package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.provider.BusinessIdProvider;
import net.bakaar.sandbox.cas.domain.vo.PNummer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CaseDomainObjectFactoryTest {

    @Mock
    private BusinessIdProvider businessIdProvider;
    @InjectMocks
    private CaseDomainObjectFactory factory;

    @Test
    public void createCase_should_create_a_case_with_an_id() {
        //Given
        String pnummer = "P12345678";
        String id = UUID.randomUUID().toString();
        LocalDate birthDate = LocalDate.now();
        given(businessIdProvider.generateId()).willReturn(id);
        //When
        Case aCase = factory.createCase(pnummer, birthDate);
        //Then
        verify(businessIdProvider).generateId();
        assertThat(aCase).isNotNull();
        assertThat(aCase.getId()).isEqualTo(id);
        assertThat(aCase.getPnummer()).isEqualTo(PNummer.of(pnummer));
        assertThat(aCase.getBirthDate()).isEqualTo(birthDate);
    }
}