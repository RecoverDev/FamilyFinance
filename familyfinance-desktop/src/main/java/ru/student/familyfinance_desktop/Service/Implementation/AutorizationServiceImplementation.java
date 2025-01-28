package ru.student.familyfinance_desktop.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Person;
import ru.student.familyfinance_desktop.RestController.AutorizationController;
import ru.student.familyfinance_desktop.Service.AutorizationService;

@RequiredArgsConstructor
@Component
public class AutorizationServiceImplementation implements AutorizationService {
    private final AutorizationController controller;

    @Autowired
    private Person person;

    @Override
    public boolean autorizate() {

        Person result = controller.login();

        if (result == null) {
            return false;
        }

        person.load(result);

        return true;
    }

}
