package net.bakaar.sandbox.cas.domain.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

@Getter
@AllArgsConstructor
public class OpenCaseCommand {
    private PNumber insured;
}
