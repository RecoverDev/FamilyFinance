package ru.student.familyfinance_desktop.Service;

import java.util.List;

import ru.student.familyfinance_desktop.Model.Expenses;

/**
 * Сервис по работе с таблицей видов расходов
 */
public interface ExpensesService {

    /**
     * Заполнение репозитория
     */
    void setExpenses();

    /**
     * Возращает полный список видов расходов пользователя
     * @return - список расходов List<Expenses>
     */
    List<Expenses> getExpenses();

    /**
     * Возвращает вид расходов по ID
     * @param id - идентификатор вида расходов
     * @return - вид расходов Expenses
     */
    Expenses getExpensesById(long id);

    /**
     * Добавляет новый вид расходов
     * @param income - новый вид расходов
     * @return - true - успех/false - неудача
     */
    boolean addExpenses(Expenses expenses);

    /**
     * Изменяет существующий вид расходов
     * @param income - измененный вид расходов
     * @return - true - успех/false - неудача
     */
    boolean editExpenses(Expenses expenses);

    /**
     * Удаляет вид расходов по ID
     * @param id - идентификатор вида расходов
     * @return - true - успех/false - неудача
     */
    boolean deleteExpensesById(long id);

}
