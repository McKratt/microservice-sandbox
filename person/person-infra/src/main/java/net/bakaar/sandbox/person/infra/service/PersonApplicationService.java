package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.PartnerFactory;
import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import org.springframework.transaction.annotation.Transactional;

public class PersonApplicationService {
    private final PartnerRepository partnerRepository;
    private final PartnerFactory partnerFactory;

    public PersonApplicationService(PartnerRepository partnerRepository, PartnerFactory factory) {
        this.partnerRepository = partnerRepository;
        this.partnerFactory = factory;
    }

    @Transactional
    public Partner createPartner(CreatePartnerCommand command) {
        return partnerRepository.putPartner(partnerFactory.createPartner(command));
    }

//    @Transactional(readOnly = true)
//    public Partner readPartner(ReadPartnerQuery query) {
//        return partnerRepository.fetchPartner(query);
//    }
}
