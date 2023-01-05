package io.github.yesminmarie.peoplemanagement.rest.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestAddressDTO {

    @NotEmpty(message = "Street must not be empty.")
    private String street;

    @NotEmpty(message = "Zip Code must not be empty.")
    private String zipCode;

    @Min(value = 1, message = "Enter only numbers for apartment number.")
    @NotNull(message = "Number must not be null.")
    private int number;

    @NotEmpty(message = "City must not be empty.")
    private String city;

    @Min(value = 1, message = "Enter only numbers for id person.")
    @NotNull(message = "Id Person Must not be null.")
    private Long idPerson;

    private Boolean main;
}
