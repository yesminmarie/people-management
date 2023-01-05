package io.github.yesminmarie.peoplemanagement.exceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(){
        super("Address not found.");
    }
}
