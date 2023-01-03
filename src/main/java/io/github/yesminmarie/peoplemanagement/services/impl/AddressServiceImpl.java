package io.github.yesminmarie.peoplemanagement.services.impl;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.repositories.AddressesRepository;
import io.github.yesminmarie.peoplemanagement.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressesRepository addressesRepository;

    @Override
    public Address save(Address address) {
        findIfPersonHasAnotherMainAddressAndSetItToFalse(address);
        return addressesRepository.save(address);
    }

    private void findIfPersonHasAnotherMainAddressAndSetItToFalse(Address address){
        if(address.isMain()){
            Address mainAddress = addressesRepository.findByMainTrueAndPerson(address.getPerson());
            if(mainAddress != null){
                mainAddress.setMain(false);
            }
        }
    }
}
