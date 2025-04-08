package ru.student.familyfinance_desktop.Service;

import java.beans.PropertyChangeListener;
import java.util.List;

import ru.student.familyfinance_desktop.Model.Target;

/**
 * Сервис по работе с таблицей целей пользователя 
 */
public interface TargetService {

    /**
     * Заполнение репозитория
     */
    void setTargets();

    /**
     * Возращает полный список целей пользователя
     * @return - список целей List<Target>
     */
    List<Target> getTargets();

    /**
     * Возвращает цель пользователя по ID
     * @param id - идентификатор цели пользователя
     * @return - цель Target
     */
    Target getTargetById(long id);

    /**
     * Добавляет новую цель пользователя
     * @param income - новая цель пользователя
     * @return - true - успех/false - неудача
     */
    boolean addTarget(Target target);

    /**
     * Изменяет существующую цель пользователя
     * @param income - измененная цельпользователя
     * @return - true - успех/false - неудача
     */
    boolean editTarget(Target target);

    /**
     * Удаляет цель пользователя по ID
     * @param id - идентификатор цели пользователя
     * @return - true - успех/false - неудача
     */
    boolean deleteTargetById(long id);

    /**
     * Установить слушателя на изменение репозотория TargetReporitory
     * @param listener
     */
    void setRepositoryListener(PropertyChangeListener listener);
    

}
