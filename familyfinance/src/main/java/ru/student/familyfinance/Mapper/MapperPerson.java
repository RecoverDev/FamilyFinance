package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.student.familyfinance.DTO.PersonDTO;
import ru.student.familyfinance.Model.Person;

@Mapper(componentModel = "spring")
public interface MapperPerson {

    PersonDTO toPersonDTO(Person person);
    List<PersonDTO> toListPersonDTO(List<Person> persons);

    @Mapping(target="password", expression="java(personDTO.getUsername() + \"_1234\")")
    @Mapping(target = "authorities", ignore = true)
    Person toPerson(PersonDTO personDTO);

    List<Person> toListPerson(List<PersonDTO> personsDTO);
}
