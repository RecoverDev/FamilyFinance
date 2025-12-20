package ru.student.familyfinance_service.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_service.Model.Person;
import ru.student.familyfinance_service.RestController.AutorizationController;
import ru.student.familyfinance_service.Service.AutorizationService;

@RequiredArgsConstructor
@Service
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
