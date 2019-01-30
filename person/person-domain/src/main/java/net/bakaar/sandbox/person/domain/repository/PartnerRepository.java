package net.bakaar.sandbox.person.domain.repository;

import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.query.ReadPartnerQuery;

public interface PartnerRepository {
    Partner push(Partner toStore);

    Partner fetchPartner(ReadPartnerQuery query);
}
