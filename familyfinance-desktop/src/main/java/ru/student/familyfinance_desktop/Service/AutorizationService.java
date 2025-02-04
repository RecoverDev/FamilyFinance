package ru.student.familyfinance_desktop.Service;

/**
 * Сервис авторизации пользователя
 */
public interface AutorizationService {

    /**
     * Авторизация пользователя
     * @return - true - успех/false - неудача
     */
    public boolean autorizate();
}