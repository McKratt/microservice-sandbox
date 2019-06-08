package net.bakaar.sandbox.cas.rest.controller;

import net.bakaar.sandbox.cas.domain.command.OpenCaseCommand;
import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.infra.service.CaseApplicationService;
import net.bakaar.sandbox.cas.rest.dto.CaseDTO;
import net.bakaar.sandbox.cas.rest.dto.CreateCaseCommandDTO;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@RestController
@RequestMapping(path = "/rest/api/v1")
public class CaseResourceController {

    private static final String CASE_ROOT_URI = "/cases";
    private final CaseApplicationService service;

    CaseResourceController(CaseApplicationService service) {
        this.service = service;
    }

    @PostMapping(value = CASE_ROOT_URI, consumes = "application/json")
    public ResponseEntity<CaseDTO> addNewCase(@RequestBody CreateCaseCommandDTO command) {
        Case createdCase = service.openCase(new OpenCaseCommand(PNumber.of(command.getInjuredNumber())));
        return created(
                fromPath(CASE_ROOT_URI + "/" + createdCase.getId())
                        .build()
                        .toUri()
        )
                .body(CaseDTO.fromCase(createdCase));
    }
}
