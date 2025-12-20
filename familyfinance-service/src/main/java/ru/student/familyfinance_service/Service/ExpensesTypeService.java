package ru.student.familyfinance_service.Service;

import java.util.List;

import ru.student.familyfinance_service.Model.ExpensesType;

/**
 * Сервис по работе с таблицей типов расходов
 */
public interface ExpensesTypeService {

    /**
     * Заполнение хранилища типов расходов
     */
    void setExpensesTypes();

    /**
     * Получение полного списка типов расходов
     * @return - список типов расходов List<ExpensesType>
     */
    List<ExpensesType> getExpensesTypes();

    /**
     * Получение типа расходов по ID
     * @param id - идентификатор типа расходов
     * @return - тип расходов ExpensesType
     */
    ExpensesType getExpensesTypeById(long id);

    /**
     * Добавление нового типа расходов
     * @param expensesType - добавляемый тип расходов
     * @return - true - успех/false - неудача
     */
    boolean addExpensesType(ExpensesType expensesType);

    /**
     * Удаление типа расходов по ID
     * @param id - идентификатор удаляемого типа расходов
     * @return - true - успех/false - неудача
     */
    boolean deleteExpensesTypeById(long id);

    /**
     * Изменение типа расходов
     * @param expensesType - изменяемый тип расходов
     * @return  - true - успех/false - неудача
     */
    boolean editExpensesType(ExpensesType expensesType);

}
