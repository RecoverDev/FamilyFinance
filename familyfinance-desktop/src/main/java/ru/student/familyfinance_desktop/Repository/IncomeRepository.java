package ru.student.familyfinance_desktop.Repository;

import java.util.List;

import ru.student.familyfinance_desktop.Model.Income;

/**
 * Хранилище видов доходов, реализует CRUD операции
 */
public interface IncomeRepository {

    /**
     * Добавляет виды доходов в список хранилища
     * @param incomes - добавляемый список
     */
    void setIncomes(List<Income> incomes);

    /**
     * Возвращает весь список видов доходов
     * @return - List<Income> список видов доходов
     */
    List<Income> getIncomes();

    /**
     * Возвращает вид доходов по ID
     * @param id - ID вида доходов 
     * @return - найденный вид доходов или NULL
     */
    Income getIncomeById(long id);

    /**
     * Добавляет новый вид доходов в список
     * @param income - добавляемый вид доходов
     * @return - true - успех/false - неудача
     */
    boolean addIncome(Income income);

    /**
     * Удаление вида доходов по ID
     * @param id - ID удаляемого вида доходов
     * @return - true - успех/false - неудача
     */
    boolean deleteIncomeById(long id);

    /**
     * Обновление (изменение) вида доходов
     * @param income - измененный вид доходов
     * @return - true - успех/false - неудача
     */
    boolean updateIncome(Income income);
}
