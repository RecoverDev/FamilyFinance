package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Person;

/**
 * Сервис описывает действия с хранилищем категорий расходов 
*/
public interface ExpensesService {

    /**
     * Добавление новой категории расходов
     * @param expenses - новая категория расходов
     * @return - добавленный вид расходов
     */
    Expenses addExpenses(Expenses expenses);

    /**
     * Удаление категории расхода по идентификатору
     * @param id - идентификатор категории расходов
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeExpenses(long id);

    /**
     * Получение списка категорий расходов
     * @return - List<Expenses>
     */
    List<Expenses> getExpenses(Person person);

    /**
     * Получение категории расходов по идентификатору
     * @param id - идентификатор
     * @return - Expenses
     */
    Expenses getExpensesById(long id);

    /**
     * Изменение категории расходов
     * @param expenses - изменяемая категория
     * @return - измененный вид расходов
     */
    Expenses editExpenses(Expenses expenses);

}
