package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.Person;

/**
 * Сервис описывает действия с хранилищем пользователей 
*/
public interface PersonService {

    /**
     * Добавление нового пользователя
     * @param person - новый пользователь
     * @return - результат операции true - успех/false - неудача
     */
    boolean addPerson(Person person);

    /**
     * Удаление пользователя по идентификатору
     * @param id - идентификатор пользователя
     * @return - результат операции true - успех/false - неудача
     */
    boolean removePerson(long id);

    /**
     * Получение списка пользователей
     * @return - List<Person>
     */
    List<Person> getPersons();

    /**
     * Получение пользователя по идентификатору
     * @param id - идентификатор пользователя
     * @return - Person
     */
    Person getPersonById(long id);

    /**
     * Получение пользователя по Username
     * @param username - Username
     * @return - Person
     */
    Person getPersonByUsername(String username);

    /**
     * Изменение пользователя
     * @param person - изменяемый пользователь
     * @return - результат операции true - успех/false - неудача
     */
    boolean editPerson(Person person);
}
