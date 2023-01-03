package io.github.yesminmarie.peoplemanagement.rest.dto;

import lombok.Data;

@Data
public class RequestAddressDTO {

    private String street;
    private String zipCode;
    private int number;
    private String city;
    private Long idPerson;
    private Boolean main;
}
