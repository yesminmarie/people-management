package io.github.yesminmarie.peoplemanagement.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.exceptions.PersonNotFoundException;
import io.github.yesminmarie.peoplemanagement.rest.dto.RequestAddressDTO;
import io.github.yesminmarie.peoplemanagement.services.AddressService;
import io.github.yesminmarie.peoplemanagement.services.PersonService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private PersonService personService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSaveNewAddress() throws Exception {
        Person person = createPerson();
        Address address = createAddress();
        RequestAddressDTO addressDTO = createAddressDTO();
        addressDTO.setIdPerson(1L);

        String jsonAddress = mapper.writeValueAsString(addressDTO);

        when(personService.getPersonById(eq(1L))).thenReturn(person);
        when(addressService.save(any(Address.class))).thenReturn(address);

        mvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAddress)
                )
                .andExpect(status().isCreated());
        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        verify(addressService).save(captor.capture());

        Address capturedAddress = captor.getValue();
        assertEquals(address.getStreet(), capturedAddress.getStreet());
        assertEquals(address.getZipCode(), capturedAddress.getZipCode());

    }

    @Test
    void shouldNotSaveNewAddressIfNotPassingIdPerson() throws Exception {
        RequestAddressDTO addressDTO = createAddressDTO();

        String jsonAddress = mapper.writeValueAsString(addressDTO);

        String jsonResponse = "Id Person Must not be null.";

        mvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAddress)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", Matchers.is(jsonResponse)));

    }

    @Test
    void shouldgetPersonAddresses() throws Exception {
        Person person = new Person();
        Long id = 1L;
        person.setId(id);

        Address address = createAddress();
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        when(personService.getPersonById(eq(id))).thenReturn(person);
        when(addressService.getPersonAddresses(person)).thenReturn(addresses);

        mvc.perform(
                        get("/addresses/" + person.getId())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].street", Matchers.is(address.getStreet())))
                .andExpect(jsonPath("$.[0].zipCode", Matchers.is(address.getZipCode())))
                .andExpect(jsonPath("$.[0].number", Matchers.is(address.getNumber())))
                .andExpect(jsonPath("$.[0].city", Matchers.is(address.getCity())))
                .andExpect(jsonPath("$.[0].main", Matchers.is(address.isMain())));
    }

    @Test
    void shouldNotGetPersonAddressesIfPersonNotExists() throws Exception {
        Long id = 1L;

        when(personService.getPersonById(id)).thenThrow(PersonNotFoundException.class);

        mvc.perform(
                        get("/addresses/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMainAddress() throws Exception {

        Long id = 1L;

        mvc.perform(
                        patch("/addresses/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()
                );
    }

    private Address createAddress(){
        Person person = createPerson();

        Address address = new Address();
        address.setId(1L);
        address.setStreet("Rua Teste");
        address.setZipCode("123456789");
        address.setNumber(123);
        address.setCity("São Paulo");
        address.setPerson(person);
        address.setMain(true);

        return address;
    }

    private RequestAddressDTO createAddressDTO(){

        RequestAddressDTO addressDTO = new RequestAddressDTO();
        addressDTO.setStreet("Rua Teste");
        addressDTO.setZipCode("123456789");
        addressDTO.setNumber(123);
        addressDTO.setCity("São Paulo");
        addressDTO.setMain(true);

        return addressDTO;
    }

    private Person createPerson(){
        Person person = new Person();
        person.setId(1L);
        person.setName("Ana");
        person.setBirthDate(LocalDate.of(2000, 5, 10));

        return person;
    }
}