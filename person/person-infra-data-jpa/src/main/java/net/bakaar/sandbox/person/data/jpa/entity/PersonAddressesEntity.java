package net.bakaar.sandbox.person.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "id.person", joinColumns = @JoinColumn(name = "PERSON_PK"))
})
@Getter
@Setter
class PersonAddressesEntity {

    @EmbeddedId
    private PersonAddressesId id;
    private boolean main;
}
