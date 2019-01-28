package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CaseDomainObjectFactoryTest {

    @Mock
    private BusinessIdRepository businessIdRepository;
    @InjectMocks
    private CaseDomainObjectFactory factory;

    @Test
    public void createCase_should_create_a_case_with_an_id() {
        //Given
        PNumber pNumber = PNumber.of(12345678);
        String id = UUID.randomUUID().toString();
        given(businessIdRepository.generateId()).willReturn(id);
        //When
        Case aCase = factory.createCase(pNumber);
        //Then
        verify(businessIdRepository).generateId();
        assertThat(aCase).isNotNull();
        assertThat(aCase.getId()).isEqualTo(id);
        assertThat(aCase.getInjured()).isSameAs(pNumber);
    }
}