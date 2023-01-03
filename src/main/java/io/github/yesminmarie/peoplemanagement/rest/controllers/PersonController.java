package io.github.yesminmarie.peoplemanagement.rest.controllers;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person save(@RequestBody Person person){
        return personService.save(person);
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable Long id){
        return personService.getPersonById(id);
    }
}
