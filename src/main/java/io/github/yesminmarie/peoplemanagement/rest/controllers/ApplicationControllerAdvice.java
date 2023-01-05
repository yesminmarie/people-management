package io.github.yesminmarie.peoplemanagement.rest.controllers;

import io.github.yesminmarie.peoplemanagement.exceptions.AddressNotFoundException;
import io.github.yesminmarie.peoplemanagement.exceptions.PersonNotFoundException;
import io.github.yesminmarie.peoplemanagement.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleAddressNotFoundException(PersonNotFoundException exception){
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleAddressNotFoundException(AddressNotFoundException exception){
        return new ApiErrors(exception.getMessage());
    }
}
