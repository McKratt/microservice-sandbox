package net.bakaar.sandbox.domain.person.rest.controller;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.rest.dto.CreatePartnerCommandDTO;
import net.bakaar.sandbox.domain.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.domain.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/rest/api/v1/partners")
public class PartnerRestController {
    private final PersonApplicationService service;
    private final PartnerDomainDtoMapper mapper;

    PartnerRestController(PersonApplicationService service,
                          PartnerDomainDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> create(@RequestBody CreatePartnerCommandDTO dto) {
        CreatePersonCommand command = CreatePersonCommand.of(dto.getName(), dto.getForename(), dto.getBirthDate());
        return ResponseEntity.created(URI.create("")).body(mapper.mapToDto(service.createPartner(command)));
    }


    @GetMapping("/{partnerId}")
    @ResponseStatus(HttpStatus.OK)
    public PartnerDTO readAPartner(@PathVariable String partnerId) {
        return mapper.mapToDto(service.readPartner(PNumber.of(partnerId)));
    }
}
