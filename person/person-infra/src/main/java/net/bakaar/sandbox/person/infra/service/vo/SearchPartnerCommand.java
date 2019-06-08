package net.bakaar.sandbox.person.infra.service.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPartnerCommand {
    private String socialNumber;
    private String pNumber;
    private String name;
}
