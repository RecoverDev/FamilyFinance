package ru.student.familyfinance_service.Service;

import ru.student.familyfinance_service.Model.RegistrationPerson;

/**
 * Регистрация нового пользователя
 */
public interface RegistrationService {

    /**
     * Регистрация пользователя
     * @param person - регистрируемый пользователь
     * @return - true - успех/false - неудача
     */
    public boolean registration(RegistrationPerson person);

}
