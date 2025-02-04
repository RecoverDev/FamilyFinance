package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;

/**
 * Сервис описывает действия с хранилищем категорий доходов 
*/
public interface IncomeService {

    /**
     * Добавление новой категории доходов
     * @param income - новая категория доходов
     * @return - сохраненный объект Income
     */
    Income addIncome(Income income);

    /**
     * Удаление категории доходов по идентификатору
     * @param id - идентификатор категории доходов
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeIncome(long id);

    /**
     * Получение списка категорий доходов
     * @param person - владелец категорий доходов
     * @return - List<Income>
     */
    List<Income> getIncomes(Person person);

    /**
     * Получение категории доходов по ID
     * @param id - идентификатор
     * @return - категория доходов
     */
    Income getIncomeById(long id);

    /**
     * Изменение категории доходов
     * @param expenses - изменяемая категория
     * @return - Отредактированный объект Income
     */
    Income editIncome(Income income);
}
