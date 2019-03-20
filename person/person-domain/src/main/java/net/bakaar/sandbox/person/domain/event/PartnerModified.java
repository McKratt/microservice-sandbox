package net.bakaar.sandbox.person.domain.event;

import lombok.Getter;
import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDateTime;

public class PartnerModified implements DomainEvent {

    private final LocalDateTime raised;

    @Getter
    private final PNumber pNumber;

    private PartnerModified(PNumber id) {
        this.pNumber = id;
        this.raised = LocalDateTime.now();
    }

    public static PartnerModified of(PNumber id) {
        return new PartnerModified(id);
    }

    @Override
    public LocalDateTime raisedAt() {
        return raised;
    }
}
