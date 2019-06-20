package net.bakaar.sandbox.domain.person.infra.service;

import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public interface BusinessNumberRepository {
    PNumber fetchNextPNumber();

    AddressNumber fetchNextAddressNumber();
}
