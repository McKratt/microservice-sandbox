package net.bakaar.sandbox.domain.number;

import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public interface BusinessNumberRepository {
    PNumber fetchNextPNumber();

    AddressNumber fetchNextAddressNumber();
}
