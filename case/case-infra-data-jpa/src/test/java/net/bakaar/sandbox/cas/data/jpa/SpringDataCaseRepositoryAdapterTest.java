package net.bakaar.sandbox.cas.data.jpa;

import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SpringDataCaseRepositoryAdapterTest {

    @Mock
    private SpringDataCaseRepository springDataCaseRepository;
    @InjectMocks
    private SpringDataCaseRepositoryAdapter springDataCaseRepositoryAdapter;

    @Test
    public void save_should_returned_the_saved_case() {
        //Given
        String pnummer = "P12345678";
        String id = UUID.randomUUID().toString();
        Case aCase = Case.builder().withBusinnessId(id).withInjured(PNumber.of(pnummer));
        given(springDataCaseRepository.save(any(CaseEntity.class))).willAnswer(invocation -> invocation.getArgument(0));
        //When
        Case returnedCase = springDataCaseRepositoryAdapter.save(aCase);
        //Then
        ArgumentCaptor<CaseEntity> caseEntityArgumentCaptor = ArgumentCaptor.forClass(CaseEntity.class);
        verify(springDataCaseRepository).save(caseEntityArgumentCaptor.capture());
        assertThat(returnedCase).isNotNull()
                .isEqualToComparingOnlyGivenFields(aCase, "injured", "id");
        CaseEntity entity = caseEntityArgumentCaptor.getValue();
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();//This id is set by the DB
        assertThat(entity.getBusinessId()).isEqualTo(id);
        assertThat(entity.getPnummer()).isEqualTo(pnummer);
    }
}