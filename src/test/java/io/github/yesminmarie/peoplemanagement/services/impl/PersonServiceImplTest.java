package io.github.yesminmarie.peoplemanagement.services.impl;

import io.github.yesminmarie.peoplemanagement.domain.entities.Person;
import io.github.yesminmarie.peoplemanagement.domain.repositories.PersonsRepository;
import io.github.yesminmarie.peoplemanagement.exceptions.PersonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonServiceImplTest {

    private PersonServiceImpl personService;

    @Mock
    private PersonsRepository personsRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.personService = new PersonServiceImpl(personsRepository);
    }

    @Test
    @DisplayName("Check if the person is found when a valid id is inserted.")
    public void checkIfThePersonIsFoundWhenValidIdIsInserted(){
        LocalDate birthDate = LocalDate.of(1990, 10, 5);
        Person person = createPerson(1L, "Ana", birthDate);

        Mockito.when(personsRepository.findById(1L)).thenReturn(Optional.of(person));

        Person foundPerson = personService.getPersonById(1L);

        assertEquals(1L, foundPerson.getId());
        assertEquals("Ana", foundPerson.getName());
        assertEquals(birthDate, foundPerson.getBirthDate());
    }

    @Test
    @DisplayName("Check if an exception is thrown if the inserted id is incorrect.")
    public void checkIfAnExceptionIsThrownIfTheInsertedIdIsIncorrect(){
        Exception exception = assertThrows(PersonNotFoundException.class,
                () -> personService.getPersonById(1L));
        assertEquals("Person not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Check if the person is saved.")
    public void checkIfThePersonIsSaved(){
        LocalDate birthDate = LocalDate.of(1990, 10, 5);
        Person person = createPerson(1L, "Ana", birthDate);

        Mockito.when(personsRepository.save(person)).thenReturn(person);

        Person savedPerson = personService.save(person);

        Mockito.verify(personsRepository).save(person);
        assertEquals(1L, savedPerson.getId());
        assertEquals("Ana", savedPerson.getName());
        assertEquals(birthDate, savedPerson.getBirthDate());
    }

    @Test
    @DisplayName("Check if the person is updated when a valid id is inserted.")
    public void checkIfThePersonIsUpdatedWhenAValidIdIsInserted(){
        LocalDate birthDate = LocalDate.of(1990, 10, 5);
        LocalDate updatedPersonBirthDate = LocalDate.of(2000, 04, 26);
        Person person = createPerson(1L, "Ana", birthDate);
        Person updatedPerson = createPerson(1L, "Maria", updatedPersonBirthDate);

        Mockito.when(personsRepository.findById(1L)).thenReturn(Optional.of(person));

        personService.update(1L, updatedPerson);

        Mockito.verify(personsRepository).save(updatedPerson);
    }

    @Test
    @DisplayName("Check if an exception is thrown when try to update a not found person.")
    public void checkIfAnExceptionIsThrownWhenTryToUpdateNotFoundPerson(){
        LocalDate birthDate = LocalDate.of(1990, 10, 5);
        Person person = createPerson(1L, "Ana", birthDate);

        Exception exception = assertThrows(PersonNotFoundException.class,
                () -> personService.update(2L, person));
        assertEquals("Person not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Check if it is returned a list of persons.")
    public void checkIfItIsReturnedListOfPersons(){
        Person person1 = createPerson(1L, "Ana", LocalDate.of(1990, 10, 5));
        Person person2 = createPerson(2L, "Maria", LocalDate.of(1995, 04, 10));
        Person person3 = createPerson(3L, "Pedro", LocalDate.of(1999, 02, 22));

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Person> pagePersons = new PageImpl(persons);

        Mockito.when(personsRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(pagePersons);

        Page<Person> allPersons = personService.findAll(pageable);

        assertEquals(allPersons.getContent(), persons);

    }

    private Person createPerson(Long id, String name, LocalDate date){
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setBirthDate(date);

        return person;
    }
}