package net.bakaar.sandbox.businessnumber.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/v1/business-number")
class BusinessNumberEndpoint {

    private final Random r = new Random();

    @GetMapping("/case-id")
    public ResponseEntity<UUID> createCaseId() {
        return ResponseEntity.ok(UUID.randomUUID());
    }

    @GetMapping("/partner-id")
    public ResponseEntity<Long> createPartnerId() {
        // Don't do that at home...
        int highest = 99999999;
        int lowest = 10000000;
        int generatedId = r.nextInt(highest - lowest) + lowest;
        return ResponseEntity.ok((long) generatedId);
    }
}
