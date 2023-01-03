package io.github.yesminmarie.peoplemanagement.services;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    Person save(Person person);

    Person getPersonById(Long id);

    void update(Long id, Person person);

    Page<Person> findAll(Pageable pageable);
}
