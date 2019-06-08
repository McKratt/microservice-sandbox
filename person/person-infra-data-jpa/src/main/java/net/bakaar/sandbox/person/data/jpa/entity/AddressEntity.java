package net.bakaar.sandbox.person.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "T_ADDRESS")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_PK", nullable = false, unique = true)
    private Long id;
    private long number;
    private String addressLine;

    @OneToMany(mappedBy = "address")
    private Set<PersonAddressesEntity> personAddresses = new HashSet<>();
}
