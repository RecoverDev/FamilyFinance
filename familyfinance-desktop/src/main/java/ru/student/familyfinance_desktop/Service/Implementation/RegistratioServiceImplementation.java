package ru.student.familyfinance_desktop.Service.Implementation;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.RegistrationPerson;
import ru.student.familyfinance_desktop.RestController.RegistrateController;
import ru.student.familyfinance_desktop.Service.RegistrationService;

@Service
@RequiredArgsConstructor
public class RegistratioServiceImplementation implements RegistrationService {
    private final RegistrateController controller;

    @Override
    public boolean registration(RegistrationPerson person) {

        return controller.registrate(person);
    }

}
