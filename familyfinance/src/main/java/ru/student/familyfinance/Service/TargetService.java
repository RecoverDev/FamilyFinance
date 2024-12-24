package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;

/**
 * Сервис описывает действия с хранилищем целей пользователя
*/
public interface TargetService {

    /**
     * Добавление новой цели
     * @param target - новая цель
     * @return - результат операции true - успех/false - неудача
     */
    boolean addTarget(Target target);

    /**
     * Удаление цели по идентификатору
     * @param id - идентификатор цели
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeTarget(long id);

    /**
     * Получение списка целей пользователей
     * @param person - пользователь
     * @return - List<Target>
     */
    List<Target> getTarget(Person person);

    /**
     * Получение цели по идентификатору
     * @param id - идентификатор
     * @return - Target
     */
    Target getTargetById(long id);

    /**
     * Изменение цели
     * @param target - изменяемая цель
     * @return - результат операции true - успех/false - неудача
     */
    boolean editTarget(Target target);
}
