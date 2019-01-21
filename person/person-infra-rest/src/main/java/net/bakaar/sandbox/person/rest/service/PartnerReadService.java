package net.bakaar.sandbox.person.rest.service;

import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;


public class PartnerReadService implements PartnerReadStore {

    private final PartnerReadStore readRepository;

    public PartnerReadService(PartnerReadStore readRepository) {
        this.readRepository = readRepository;
    }

    @Transactional(readOnly = true)
    public PartnerDTO fetchPartnerById(PNumber pNumber) {
        return readRepository.fetchPartnerById(pNumber);
    }
}
