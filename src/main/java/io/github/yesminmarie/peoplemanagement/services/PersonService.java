package io.github.yesminmarie.peoplemanagement.services;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;

public interface PersonService {
    Person save(Person person);

    Person getPersonById(Long id);
}
