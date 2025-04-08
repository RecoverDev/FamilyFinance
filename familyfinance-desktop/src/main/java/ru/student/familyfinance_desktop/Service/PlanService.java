package ru.student.familyfinance_desktop.Service;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.List;

import ru.student.familyfinance_desktop.Model.Plan;

/**
 * Сервис по работе с таблицей планов пользователя 
 */
public interface PlanService {

    /**
     * Заполнение репозитория
     * @param period - начало периода выборки записей о планах
     */
    void setPlans(LocalDate period);

    /**
     * Получение полного списка планов пользователя
     * @return - список планов пользователя List<Plan>
     */
    List<Plan> getPlans();

    /**
     * Получение записи плана по ID
     * @param id - идентификатор записи
     * @return - запись плана
     */
    Plan getPlanById(long id);

    /**
     * Добавляет новую запись плана
     * @param plan - новая запись плана
     * @return - true - успех/false - неудача
     */
    boolean addPlan(Plan plan);

    /**
     * Изменяет запись плана
     * @param plan - измененная запись плана
     * @return - true - успех/false - неудача
     */
    boolean editPlan(Plan plan);

    /**
     * Удаляет запись плана по ID
     * @param id - идентификатор удаляемой записи плана
     * @return - true - успех/false - неудача
     */
    boolean deletePlanById(long id);

    /**
     * Установить слушателя на изменение репозотория PlanReporitory
     * @param listener
     */
    void setRepositoryListener(PropertyChangeListener listener);

}
