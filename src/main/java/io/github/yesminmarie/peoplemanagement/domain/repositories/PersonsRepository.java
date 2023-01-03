package io.github.yesminmarie.peoplemanagement.domain.repositories;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonsRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p left join fetch p.adresses where p.id = :id")
    Person findPersonFetchAddress(@Param("id") Long id);
}
