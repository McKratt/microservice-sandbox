package net.bakaar.sandbox.person.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PersonAddressesId implements Serializable {

    @ManyToOne
    private PersonEntity person;

    @OneToOne
    private AddressEntity address;
}
