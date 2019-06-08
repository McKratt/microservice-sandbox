package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

class CaseDomainObjectFactory {

    private final BusinessIdRepository businessIdRepository;

    CaseDomainObjectFactory(BusinessIdRepository businessIdRepository) {
        this.businessIdRepository = businessIdRepository;
    }

    Case createCase(PNumber pNumber) {
        return Case.builder()
                .withBusinnessId(businessIdRepository.generateId())
                .withInjured(pNumber);
    }
}
