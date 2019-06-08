package net.bakaar.sandbox.person.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class PersonEntity {

    @Id
    @GeneratedValue
    @Column(name = "PERSON_ID", nullable = false, unique = true)
    private Long id;

    private String name;

    private String forename;

    private long pNumber;

    @OneToMany(mappedBy = "id.address", cascade = CascadeType.ALL)
    private List<PersonAddressesEntity> personAddresses = new ArrayList<>();

    @Column(name = "birth_date")
    private LocalDate birthDate;
}
