package net.bakaar.sandbox.person.domain;

import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.vo.CreatePartnerCommand;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerFactoryTest {


    private PartnerFactory factory = new PartnerFactory(() -> PNumber.of(12345678));

    @Test
    public void createPartner_should_call_BusinessNumberRepository_and_return_a_partner_containing_command_fields() {
        //Given
        CreatePartnerCommand command = CreatePartnerCommand.of("name", "forename", LocalDate.now().minus(1, ChronoUnit.YEARS));
        //When
        Partner createdPartner = factory.createPartner(command);
        //Then
        assertThat(createdPartner).isNotNull();
        assertThat(createdPartner.getName().getLine()).isEqualTo(command.getName());
        assertThat(createdPartner.getForename().getLine()).isEqualTo(command.getForename());
        assertThat(createdPartner.getBirthDate()).isEqualTo(command.getBirthDate());
        assertThat(createdPartner.getId()).isNotNull();
    }
}