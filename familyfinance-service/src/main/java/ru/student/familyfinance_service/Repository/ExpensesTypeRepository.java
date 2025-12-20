package ru.student.familyfinance_service.Repository;

import java.util.List;

import ru.student.familyfinance_service.Model.ExpensesType;

/**
 * Хранилище типов расходов, реализует CRUD операции
 */
public interface ExpensesTypeRepository {

    /**
     * Добавляет типы расходов в хранилище
     * @param listExpensesType - список типов расходов List<ExpensesType>
     */
    void setExpensesType(List<ExpensesType> listExpensesType);

    /**
     * Возвращает полный список типов расходов
     * @return - список типов расходов List<ExpensesType>
     */
    List<ExpensesType> getExpensesType();

    /**
     * Возвращает тип расходов по ID
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
     * Изменение типа расходов
     * @param expensesType - изменяемый тип расходов
     * @return - true - успех/false - неудача
     */
    boolean editExpensesType(ExpensesType expensesType);

    /**
     * Удаление типа расходов
     * @param id - идентификатор удаляемого типа расходов
     * @return - true - успех/false - неудача
     */
    boolean deleteExpensesTypeById(long id);

}
