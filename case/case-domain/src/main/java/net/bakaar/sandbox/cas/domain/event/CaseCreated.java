package net.bakaar.sandbox.cas.domain.event;

import net.bakaar.sandbox.cas.domain.vo.PNummer;
import net.bakaar.sandbox.event.common.DomainEvent;

public class CaseCreated implements DomainEvent {
    private final String id;
    private final PNummer pnummer;

    public CaseCreated(String id, PNummer pnummer) {
        this.id = id;
        this.pnummer = pnummer;
    }

    public String getId() {
        return id;
    }

    public PNummer getPnummer() {
        return pnummer;
    }
}
