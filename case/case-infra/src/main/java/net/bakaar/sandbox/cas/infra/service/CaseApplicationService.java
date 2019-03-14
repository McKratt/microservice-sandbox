package net.bakaar.sandbox.cas.infra.service;

import net.bakaar.sandbox.cas.domain.OpenCaseUseCase;
import net.bakaar.sandbox.cas.domain.command.OpenCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import org.springframework.transaction.annotation.Transactional;

public class CaseApplicationService implements OpenCaseUseCase {

    private final OpenCaseUseCase domainService;

    public CaseApplicationService(OpenCaseUseCase domainService) {
        this.domainService = domainService;
    }

    @Transactional
    @Override
    public Case openCase(OpenCaseCommand command) {
        return domainService.openCase(command);
    }
}
