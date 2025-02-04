package ru.student.familyfinance_desktop.Service;

import java.util.List;

import ru.student.familyfinance_desktop.Model.Income;

/**
 * Сервис по работе с таблицей видов доходов 
 */
public interface IncomeService {

    /**
     * Заполнение репозитория
     */
    void setIncomes();

    /**
     * Возращает полный список видов доходов пользователя
     * @return - список доходов List<Income>
     */
    List<Income> getIncomes();

    /**
     * Возвращает вид доходов по ID
     * @param id - идентификатор вида доходов
     * @return - вид доходов Income
     */
    Income getIncomeById(long id);

    /**
     * Удаляет вид доходов по ID
     * @param id - идентификатор вида доходов
     * @return - true - успех/false - неудача
     */
    boolean deleteIncomeById(long id);

    /**
     * Добавляет новый вид доходов
     * @param income - новый вид доходов
     * @return - true - успех/false - неудача
     */
    boolean addIncome(Income income);

    /**
     * Изменяет существующий вид доходов
     * @param income - измененный вид доходов
     * @return - true - успех/false - неудача
     */
    boolean editIncome(Income income);

}
