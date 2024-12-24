package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.ExpensesType;

/**
 * Сервис описывает действия с хранилищем типов категорий расходов 
*/
public interface ExpensesTypeService {

    /**
     * Добавление нового типа категории расходов
     * @param expenses - новый тип категории расходов
     * @return - результат операции true - успех/false - неудача
     */
    boolean addExpensesType(ExpensesType expensesType);

    /**
     * Удаление типа категории расхода по идентификатору
     * @param id - идентификатор типа категории расходов
     */
    boolean removeExpensesType(long id);

    /**
     * Получение списка типов категорий расходов
     * @return - List<ExpensesType>
     */
    List<ExpensesType> getExpensesType();

    /**
     * Получение типа категории расходов по идентификкатору
     * @param id - идентификатор
     * @return - ExpensesType
     */
    ExpensesType getExpensesTypeById(long id);

    /**
     * Изменение типа категории расходов
     * @param expenses - изменяемый тип категории расходов
     * @return - результат операции true - успех/false - неудача
     */
    boolean editExpensesType(ExpensesType expensesType);
}
