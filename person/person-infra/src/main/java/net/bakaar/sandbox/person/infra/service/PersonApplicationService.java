package net.bakaar.sandbox.person.infra.service;

import net.bakaar.sandbox.person.domain.PartnerRepository;
import net.bakaar.sandbox.person.domain.entity.Address;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.AddressNumber;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.vo.SearchPartnerQuery;
import net.bakaar.sandbox.person.infra.service.vo.CreateAddressCommand;
import net.bakaar.sandbox.person.infra.service.vo.SearchPartnerCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PersonApplicationService {
    private final PartnerRepository partnerRepository;
    private final PartnerFactory partnerFactory;
    private final BusinessNumberRepository idRepository;

    public PersonApplicationService(PartnerRepository partnerRepository, PartnerFactory factory, BusinessNumberRepository idRepository) {
        this.partnerRepository = partnerRepository;
        this.partnerFactory = factory;
        this.idRepository = idRepository;
    }

    @Transactional
    public Partner createPartner(CreatePartnerCommand command) {
        return partnerRepository.putPartner(partnerFactory.createPartner(command));
    }

    @Transactional(readOnly = true)
    public List<Partner> searchPartners(SearchPartnerCommand command) {
        List<Partner> partners = new ArrayList<>();
        if (!isEmpty(command.getPNumber())) {
            partners.add(partnerRepository.fetchPartnerById(PNumber.of(command.getPNumber())));
        }
        if (!isEmpty(command.getName()) || !isEmpty(command.getSocialNumber())) {
            SearchPartnerQuery query = new SearchPartnerQuery();
            query.setName(command.getName());
            query.setSocialSocialNumber(command.getSocialNumber());
            partners.addAll(partnerRepository.searchPartner(query));
        }
        return partners;
    }

    @Transactional(readOnly = true)
    public Partner readPartner(PNumber id) {
        return partnerRepository.fetchPartnerById(id);
    }

    @Transactional
    public Partner addAddressToPartner(PNumber partnerId, CreateAddressCommand command) {
        Partner partner = partnerRepository.fetchPartnerById(partnerId);
        if (partner == null) {
            throw new IllegalArgumentException(String.format("The partner id %s doesn't exist", partnerId.format()));
        }
        // TODO move this part inside the factory
        AddressNumber addressId = idRepository.fetchNextAddressNumber();
        Address newAddress = Address.of(addressId, command.getAddress());

        partner.addNewAddress(newAddress);
        return partnerRepository.putPartner(partner);
    }
}
