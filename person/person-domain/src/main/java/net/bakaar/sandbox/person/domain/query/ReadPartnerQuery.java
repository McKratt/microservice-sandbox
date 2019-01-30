package net.bakaar.sandbox.person.domain.query;

import lombok.Getter;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

@Getter
public class ReadPartnerQuery {
    private final PNumber numberOfPartnerToFound;

    public ReadPartnerQuery(PNumber numberOfPartnerToFound) {
        this.numberOfPartnerToFound = numberOfPartnerToFound;
    }
}
