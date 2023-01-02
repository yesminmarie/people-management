package io.github.yesminmarie.peoplemanagement.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    private int number;

    private String city;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
