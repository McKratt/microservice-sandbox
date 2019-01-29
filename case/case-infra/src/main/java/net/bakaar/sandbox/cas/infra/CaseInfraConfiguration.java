package net.bakaar.sandbox.cas.infra;

import net.bakaar.sandbox.cas.domain.CaseService;
import net.bakaar.sandbox.cas.domain.CreateCaseUseCase;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import net.bakaar.sandbox.cas.domain.repository.CaseRepository;
import net.bakaar.sandbox.cas.infra.service.CaseApplicationService;
import net.bakaar.sandbox.event.domain.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseInfraConfiguration {

    @Bean
    public CreateCaseUseCase applicationService(EventStore eventStore,
                                                CaseRepository caseRepository,
                                                BusinessIdRepository businessNumberRepository) {
        return new CaseApplicationService(new CaseService(eventStore, caseRepository, businessNumberRepository));
    }
}
