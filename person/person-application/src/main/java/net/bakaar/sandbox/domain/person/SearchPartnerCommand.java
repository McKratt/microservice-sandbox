package net.bakaar.sandbox.domain.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class SearchPartnerCommand {
    private String socialNumber;
    private String pNumber;
    private String name;
}
