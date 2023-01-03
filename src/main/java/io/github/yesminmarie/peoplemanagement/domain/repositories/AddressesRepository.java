package io.github.yesminmarie.peoplemanagement.domain.repositories;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AddressesRepository extends JpaRepository<Address, Long> {
    Address findByMainTrueAndPerson(Person person);
    Set<Address> findByPerson(Person person);
}
