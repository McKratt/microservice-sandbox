package net.bakaar.sandbox.cas.domain;

import net.bakaar.sandbox.cas.domain.command.OpenCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;

public interface OpenCaseUseCase {

    Case openCase(OpenCaseCommand command);
}
