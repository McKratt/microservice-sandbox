package net.bakaar.sandbox.cas.domain.event;

import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDateTime;

public class CaseCreated implements DomainEvent {
    private final String id;
    private final PNumber pNumber;
    private final LocalDateTime raised;

    public CaseCreated(String id, PNumber pNumber) {
        this.id = id;
        this.pNumber = pNumber;
        this.raised = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public PNumber getPNumber() {
        return pNumber;
    }

    @Override
    public LocalDateTime raisedAt() {
        return raised;
    }
}
