package net.bakaar.sandbox.person.domain;

import net.bakaar.sandbox.shared.domain.vo.PNumber;

public interface BusinessNumberRepository {
    PNumber fetchNextPNumber();
}
