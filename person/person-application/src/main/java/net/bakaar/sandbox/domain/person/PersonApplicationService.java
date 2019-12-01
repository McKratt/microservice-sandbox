package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

public interface PersonApplicationService {
    @Transactional
    Person createPerson(CreatePersonCommand command);

    @Transactional(readOnly = true)
    Person readPerson(PNumber id);
}
