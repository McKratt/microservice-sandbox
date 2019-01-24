package net.bakaar.sandbox.cas.data.jpa;

import net.bakaar.sandbox.cas.domain.repository.CaseRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {SpringDataCaseRepository.class})
@EntityScan(basePackageClasses = {CaseEntity.class})
public class CaseDataJpaConfiguration {

    @Bean
    public CaseRepository caseRepository(SpringDataCaseRepository caseRepository) {
        return new SpringDataCaseRepositoryAdapter(caseRepository);
    }
}
