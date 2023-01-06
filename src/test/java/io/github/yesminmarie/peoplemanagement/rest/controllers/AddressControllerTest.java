package io.github.yesminmarie.peoplemanagement.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yesminmarie.peoplemanagement.domain.entities.Address;
import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.rest.dto.RequestAddressDTO;
import io.github.yesminmarie.peoplemanagement.services.AddressService;
import io.github.yesminmarie.peoplemanagement.services.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Person person = createPerson(1L, "Ana", LocalDate.of(1990, 10, 5));
        Address address = createAddress(
                1L, "Rua Teste", "123456789", 123, "São Paulo", person, true);
        RequestAddressDTO addressDTO = createAddressDTO(
                "Rua Teste", "123456789", 123, "São Paulo",  1L, true);

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
    void getPersonAddresses() {
    }

    @Test
    void updateMainAddress() {
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

    private RequestAddressDTO createAddressDTO(String street, String zipCode, int number, String city, Long idPerson, Boolean main){

        RequestAddressDTO address = new RequestAddressDTO();
        address.setStreet(street);
        address.setZipCode(zipCode);
        address.setNumber(number);
        address.setCity(city);
        address.setIdPerson(idPerson);
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