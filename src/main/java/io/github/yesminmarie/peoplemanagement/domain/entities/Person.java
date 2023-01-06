package io.github.yesminmarie.peoplemanagement.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name must not be empty.")
    private String name;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "20/10/1990")
    @NotNull(message = "Birth date must not be mull.")
    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Address> addresses;
}
