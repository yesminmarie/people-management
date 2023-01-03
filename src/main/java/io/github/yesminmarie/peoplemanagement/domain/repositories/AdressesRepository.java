package io.github.yesminmarie.peoplemanagement.domain.repositories;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressesRepository extends JpaRepository<Address, Long> {

}
