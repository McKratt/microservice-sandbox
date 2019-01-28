package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.command.CreateCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;

public interface CreateCaseUseCase {

    Case createCase(CreateCaseCommand command);
}
