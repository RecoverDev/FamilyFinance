package ru.student.familyfinance.Service;

import java.time.LocalDate;
import java.util.List;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;

/**
 * Сервис описывает действия с планом пользователя 
*/
public interface PlanService {

    /**
     * Добавление нового пункта план
     * @param person - новый пункт план
     * @return добавленный пункт плана
     */
    Plan addPlan(Plan plan);

    /**
     * Удаление пункта плана по идентификатору
     * @param id - идентификатор пользователя
     * @return - результат операции true - успех/false - неудача
     */
    boolean removePlan(long id);

    /**
     * Получение списка пунктов плана пользователей за месяц
     * @param person - пользователь
     * @param month - номер месяца
     * @return - List<Plan>
     */
    List<Plan> getPlans(Person person, LocalDate month);

    /**
     * Получение списка пунктов плана пользователя за несколько месяцев
     * @param person - пользователь
     * @param begin - начало периода выборки
     * @param end - конец периода выборки
     * @return - List<Plan>
     */
    List<Plan> getPlansSeveralMonths(Person person, LocalDate begin, LocalDate end);

    /**
     * Получение планируемой суммы дохода
     * @param person - пользователь
     * @param month - номер месяца
     * @return - сумма дохода
     */
    double getIncomePlan(Person person, LocalDate month);

    /**
     * Получение планируемой суммы расходов
     * @param person - пользователь
     * @param month - номер месяца
     * @return - сумма расходов
     */
    double getExpensesPlan(Person person, LocalDate month);

    /**
     * Получение планируемой суммы на реализацию цели
     * @param person - пользователь
     * @param month - номер месяца
     * @return - сумма на реализацию цели
     */
    double getTargetPlan(Person person, LocalDate month);

    /**
     * Изменение пункта плана
     * @param plan - изменяемый пункт плана
     * @return - отредактированный пункт плана
     */
    Plan editPlan(Plan plan);

}
