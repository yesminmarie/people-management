package io.github.yesminmarie.peoplemanagement.rest.controllers;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Person person){
        personService.update(id, person);
    }

    @GetMapping
    public Page<Person> getAllPersons(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable){
        return personService.findAll(pageable);
    }
}
