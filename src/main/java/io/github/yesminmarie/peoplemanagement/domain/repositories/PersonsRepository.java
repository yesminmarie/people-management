package io.github.yesminmarie.peoplemanagement.domain.repositories;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Person, Long> {

}
