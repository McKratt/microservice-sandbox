package net.bakaar.sandbox.person.domain;

import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.SearchPartnerQuery;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.util.List;

public interface PartnerRepository {

    Partner putPartner(Partner partner);

    Partner fetchPartnerById(PNumber id);

    List<Partner> searchPartner(SearchPartnerQuery query);
}
