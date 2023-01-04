package io.github.yesminmarie.peoplemanagement.services.impl;

import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.domain.repositories.AddressesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceImplTest {

    private AddressServiceImpl addressService;

    @Mock
    private AddressesRepository addressesRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.addressService = new AddressServiceImpl(addressesRepository);
    }

    @Test
    @DisplayName("Check if address is saved.")
    public void checkIfAddressIsSaved(){

        Person person = createPerson(1L, "Ana", LocalDate.of(1990, 10, 5));
        Address address = createAddress(1L, "Rua Teste", "123456789", 123, "São Paulo", person, true);

        Mockito.when(addressesRepository.save(address)).thenReturn(address);

        Address savedAddress = addressService.save(address);

        Mockito.verify(addressesRepository).save(address);
        assertEquals(1L, savedAddress.getId());
        assertEquals("Rua Teste", savedAddress.getStreet());
        assertEquals("123456789", savedAddress.getZipCode());
        assertEquals(123, savedAddress.getNumber());
        assertEquals("São Paulo", savedAddress.getCity());
        assertEquals(true, savedAddress.isMain());
        assertEquals(person, savedAddress.getPerson());
    }

    @Test
    @DisplayName("Check if person has another main address and set it to false before save address.")
    public void checkIfPersonHasAnotherMainAddressAndSetItToFalseBeforeSaveAddress(){

        Person person = createPerson(1L, "Ana", LocalDate.of(1990, 10, 5));
        Address address1 = createAddress(1L, "Rua Teste", "123456789", 123, "São Paulo", person, true);
        Address address2 = createAddress(2L, "Rua Teste2", "123456789", 456, "São Paulo", person, true);

        Mockito.when(addressesRepository.findByMainTrueAndPerson(address1.getPerson())).thenReturn(address1);
        Mockito.when(addressesRepository.save(address2)).thenReturn(address2);

        Address savedAddress = addressService.save(address2);

        Mockito.verify(addressesRepository).save(address2);
        assertEquals(2L, savedAddress.getId());
        assertEquals(false, address1.isMain());
        assertEquals(true, savedAddress.isMain());
    }

    @Test
    @DisplayName("check if it is getting the person addresses.")
    public void checkIfItIsGettingThePersonAddresses(){
        Person person = createPerson(1L, "Ana", LocalDate.of(1990, 10, 5));
        Address address1 = createAddress(1L, "Rua Teste", "123456789", 123, "São Paulo", person, true);
        Address address2 = createAddress(2L, "Rua Teste2", "123456789", 456, "São Paulo", person, true);

        Set<Address> addresses = new HashSet<>();
        addresses.add(address1);
        addresses.add(address2);

        Mockito.when(addressesRepository.findByPerson(person)).thenReturn(addresses);

        Set<Address> foundAddresses = addressService.getPersonAddresses(person);

        assertEquals(foundAddresses, addresses);
    }

    @Test
    @DisplayName("check if it is getting the person addresses.")
    public void checkIfItIsUpdatingTheMainAddress(){
        Person person = createPerson(1L, "Ana", LocalDate.of(1990, 10, 5));
        Address addressThatMustBeFalse = createAddress(1L, "Rua Teste", "123456789", 123, "São Paulo", person, true);
        Address addressThatMustBeTrue = createAddress(2L, "Rua Teste2", "123456789", 456, "São Paulo", person, true);

        Mockito.when(addressesRepository.findById(2L)).thenReturn(Optional.of(addressThatMustBeTrue));
        Mockito.when(addressesRepository.findByMainTrueAndPerson(addressThatMustBeFalse.getPerson())).thenReturn(addressThatMustBeFalse);
        Mockito.when(addressesRepository.save(addressThatMustBeTrue)).thenReturn(addressThatMustBeTrue);

        addressService.updateMainAddress(2L);

        assertEquals(2L, addressThatMustBeTrue.getId());
        assertEquals(false, addressThatMustBeFalse.isMain());
        assertEquals(true, addressThatMustBeTrue.isMain());

    }

    @Test
    @DisplayName("check if an exception is thrown if not found address when try updating the main address.")
    public void checkIfAnExceptionIsThrownIfNotFoundAddressWhenTryUpdatingTheMainAddress(){
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> addressService.updateMainAddress(1L));
        assertEquals("404 NOT_FOUND \"Address not found.\"", exception.getMessage());

    }
    private Address createAddress(Long id, String street, String zipCode, int number, String city, Person person, Boolean main){

        Address address = new Address();
        address.setId(id);
        address.setStreet(street);
        address.setZipCode(zipCode);
        address.setNumber(number);
        address.setCity(city);
        address.setPerson(person);
        address.setMain(main);

        return address;
    }

    private Person createPerson(Long id, String name, LocalDate date){
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setBirthDate(date);

        return person;
    }
}