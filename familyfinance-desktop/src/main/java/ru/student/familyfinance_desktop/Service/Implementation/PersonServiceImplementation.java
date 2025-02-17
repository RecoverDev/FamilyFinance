package ru.student.familyfinance_desktop.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.RestController.RegistrateController;
import ru.student.familyfinance_desktop.Service.PersonService;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired
    private RegistrateController controller;

    @Override
    public boolean editPerson(Person person) {
        return controller.editPerson(person);
    }

}
