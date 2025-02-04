package ru.student.familyfinance_desktop.Service;

import ru.student.familyfinance_desktop.Model.RegistrationPerson;

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
