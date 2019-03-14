package net.bakaar.sandbox.cas.data.jpa;

import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.domain.repository.CaseRepository;

public class SpringDataCaseRepositoryAdapter implements CaseRepository {

    private final SpringDataCaseRepository repository;

    public SpringDataCaseRepositoryAdapter(SpringDataCaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Case save(Case aCase) {
        CaseEntity entity = CaseEntity.fromCase(aCase);
        return repository.save(entity).toCase();
    }
}
