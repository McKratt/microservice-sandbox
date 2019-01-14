package net.bakaar.sandbox.person.domain.event;

import lombok.Getter;
import net.bakaar.sandbox.event.domain.Event;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerCreated implements Event {

    @Getter
    private final PNumber pNumber;

    private PartnerCreated(PNumber id) {
        this.pNumber = id;
    }

    public static PartnerCreated of(PNumber id) {
        return new PartnerCreated(id);
    }
}
