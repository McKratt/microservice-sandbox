package net.bakaar.sandbox.cas.infra.service;

import net.bakaar.sandbox.cas.domain.CreateCaseUseCase;
import net.bakaar.sandbox.cas.domain.command.CreateCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import org.springframework.transaction.annotation.Transactional;

public class CaseApplicationService implements CreateCaseUseCase {

    private final CreateCaseUseCase domainService;

    public CaseApplicationService(CreateCaseUseCase domainService) {
        this.domainService = domainService;
    }

    @Transactional
    @Override
    public Case createCase(CreateCaseCommand command) {
        return domainService.createCase(command);
    }
}
