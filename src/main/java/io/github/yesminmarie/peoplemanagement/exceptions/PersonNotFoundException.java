package io.github.yesminmarie.peoplemanagement.exceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(){
        super("Person not found.");
    }
}
