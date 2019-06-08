package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.vo.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public interface BusinessNumberRepository {
    PNumber fetchNextPNumber();

    AddressNumber fetchNextAddressNumber();
}
