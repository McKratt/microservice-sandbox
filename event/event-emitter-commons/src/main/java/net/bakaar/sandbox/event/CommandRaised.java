package net.bakaar.sandbox.event;

import lombok.Getter;
import net.bakaar.sandbox.event.domain.DomainCommand;

@Getter
public
class CommandRaised extends MessageRaised {
    private final DomainCommand command;

    public CommandRaised(DomainCommand command) {
        super();
        this.command = command;
    }
}
