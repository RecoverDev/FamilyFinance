package ru.student.familyfinance.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ru.student.familyfinance.DTO.PersonDTO;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Service.PersonService;

@Mapper(componentModel = "spring")
public abstract class MapperPerson {

    @Autowired
    protected PersonService personService;

    public abstract PersonDTO toPersonDTO(Person person);
    public abstract List<PersonDTO> toListPersonDTO(List<Person> persons);

    @Mapping(target="password", expression="java(getPassword(personDTO))")
    @Mapping(target = "authorities", ignore = true)
    public abstract Person toPerson(PersonDTO personDTO);

    public abstract List<Person> toListPerson(List<PersonDTO> personsDTO);

    protected String getPassword(PersonDTO personDTO) {
        Person person = personService.getPersonById(personDTO.getId());
        if (person == null) {
            return personDTO.getUsername() + "_1234";
        } else {
            return person.getPassword();
        }
    }
}
