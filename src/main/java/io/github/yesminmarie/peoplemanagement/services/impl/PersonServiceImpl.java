package io.github.yesminmarie.peoplemanagement.services.impl;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.domain.repositories.PersonsRepository;
import io.github.yesminmarie.peoplemanagement.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonsRepository personsRepository;

    @Override
    public Person save(Person person) {
        return personsRepository.save(person);
    }

    @Override
    public Person getPersonById(Long id) {
        return personsRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found."));
    }

    @Override
    public void update(Long id, Person person) {
        personsRepository
                .findById(id)
                .map(existingPerson -> {
                    person.setId(existingPerson.getId());
                    personsRepository.save(person);
                    return existingPerson;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found."));
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personsRepository.findAll(pageable);
    }
}
