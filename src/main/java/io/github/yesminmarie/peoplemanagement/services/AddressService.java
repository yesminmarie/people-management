package io.github.yesminmarie.peoplemanagement.services;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;

import java.util.Set;

public interface AddressService {
    Address save(Address address);

    Set<Address> getPersonAddresses(Person person);

    void updateMainAddress(Long idAddress);
}
