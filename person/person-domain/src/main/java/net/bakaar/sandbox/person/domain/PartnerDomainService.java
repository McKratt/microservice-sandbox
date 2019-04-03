package net.bakaar.sandbox.person.domain;

import net.bakaar.sandbox.event.domain.EventStore;
import net.bakaar.sandbox.person.domain.entity.Partner;

public class PartnerDomainService {
    private final EventStore eventStore;

    public PartnerDomainService(EventStore store) {
        this.eventStore = store;
    }

    public Partner modify(Partner modifiedPartner) {
        return null;
    }
}
