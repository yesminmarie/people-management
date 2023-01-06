package io.github.yesminmarie.peoplemanagement.rest.controllers;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @Test
    public void shouldSaveNewPerson() throws Exception {
        Person person = createPerson();

        String jsonPerson = "{\"name\":\"Ana\", \"birthDate\":\"05/02/1990\"}";

        when(personService.save(any(Person.class))).thenReturn(person);

        mvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)
        )
                .andExpect(status().isCreated());
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).save(captor.capture());

        Person capturedPerson = captor.getValue();
        assertEquals(person.getName(), capturedPerson.getName());
        assertEquals(person.getBirthDate(), capturedPerson.getBirthDate());
    }

    @Test
    public void shouldNotSaveNewPersonIfNotPassingName() throws Exception {
        Person person = createPerson();

        String jsonPerson = "{\"birthDate\":\"05/02/1990\"}";
        String jsonResponse = "Name must not be empty.";

        when(personService.save(any(Person.class))).thenReturn(person);

        mvc.perform(
                        post("/persons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPerson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", Matchers.is(jsonResponse)));
    }

    @Test
    public void shouldGetPersonById() throws Exception {
        Person person = new Person();
        Long id = 1L;
        person.setId(id);

        when(personService.getPersonById(eq(id))).thenReturn(person);

        mvc.perform(
                get("/persons/" + person.getId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(person.getName())))
                .andExpect(jsonPath("$.birthDate", Matchers.is(person.getBirthDate())));
    }

    @Test
    void shouldUpdatePerson() throws Exception {
        Person person = createPerson();
        Long id = 1L;
        person.setId(id);

        String jsonPerson = "{\"name\":\"Ana\", \"birthDate\":\"05/02/1990\"}";

        when(personService.getPersonById(eq(id))).thenReturn(person);

        mvc.perform(
                put("/persons/" + person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                        .andExpect(status().isNoContent()
        );

    }

    private Person createPerson(){
        Person person = new Person();
        person.setName("Ana");
        person.setBirthDate(LocalDate.of(1990, 2, 05));

        return person;
    }
}