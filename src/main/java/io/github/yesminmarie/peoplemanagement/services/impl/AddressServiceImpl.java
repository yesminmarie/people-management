package io.github.yesminmarie.peoplemanagement.services.impl;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.domain.repositories.AddressesRepository;
import io.github.yesminmarie.peoplemanagement.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressesRepository addressesRepository;

    @Override
    public Address save(Address address) {
        if (address.isMain()) {
            findIfPersonHasAnotherMainAddressAndSetItToFalse(address);
        }
        return addressesRepository.save(address);
    }

    @Override
    public Set<Address> getPersonAddresses(Person person) {
        return addressesRepository.findByPerson(person);
    }

    @Override
    public void updateMainAddress(Long idAddress) {
        Address foundAddress = addressesRepository
                .findById(idAddress)
                .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found."));

        findIfPersonHasAnotherMainAddressAndSetItToFalse(foundAddress);
        foundAddress.setMain(true);
        addressesRepository.save(foundAddress);
    }

    private void findIfPersonHasAnotherMainAddressAndSetItToFalse(Address address) {
        Address mainAddress = addressesRepository.findByMainTrueAndPerson(address.getPerson());
        if (mainAddress != null) {
            mainAddress.setMain(false);
        }
    }
}
