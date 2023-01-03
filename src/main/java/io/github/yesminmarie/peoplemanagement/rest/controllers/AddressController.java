package io.github.yesminmarie.peoplemanagement.rest.controllers;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.rest.dto.RequestAddressDTO;
import io.github.yesminmarie.peoplemanagement.services.AddressService;
import io.github.yesminmarie.peoplemanagement.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Address save(@RequestBody RequestAddressDTO dto){
        Long idPerson = dto.getIdPerson();
        Person person = personService.getPersonById(idPerson);

        Address address = new Address();
        BeanUtils.copyProperties(dto, address);
        address.setPerson(person);

        return addressService.save(address);
    }

    @GetMapping("{idPerson}")
    public Set<Address> getPersonAddresses(@PathVariable Long idPerson){
        Person person = personService.getPersonById(idPerson);
        return addressService.getPersonAddresses(person);
    }

    @PatchMapping("{idAddress}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMainAddress(
            @PathVariable Long idAddress){
        addressService.updateMainAddress(idAddress);
    }
}
