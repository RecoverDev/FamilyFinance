package ru.student.familyfinance.Service;

import java.time.LocalDate;
import java.util.List;

import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Person;

/**
 * Сервис описывает действия с хранилищем записей о ежедневных операциях 
*/
public interface GrossBookService {

    /**
     * Добавление новой записи
     * @param grossBook - новая запись
     * @return - результат операции true - успех/false - неудача
     */
    boolean addGrossBook(GrossBook grossBook);

    /**
     * Удаление записи по идентификатору
     * @param id - идентификатор записи
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeGrossBook(long id);

    /**
     * Получение списка записей за период
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - List<GrossBook>
     */
    List<GrossBook> getGrossBooks(LocalDate begin, LocalDate end, Person person);

    /**
     * Получение суммы всех доходов за период
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - сумма доходов
     */
    double getIncomeByPeriod(LocalDate begin, LocalDate end, Person person);

    /**
     * Получение всех записей о доходах за период
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - List<GrossBook>
     */
    List<GrossBook> getListIncomesByPeriod(LocalDate begin, LocalDate end, Person person);

    /**
     * Получение суммы всех расходов за период
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - сумма свех расходов
     */
    double getExpensesByPeriod(LocalDate begin, LocalDate end, Person person);

    /**
     * Получение списка всех расходов за период
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - List<GrossBook>
     */
    List<GrossBook> getListExpensesByPeriod(LocalDate begin, LocalDate end, Person person);

    /**
     * Получение записей о накоплении суммы на цель
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - сумма, отложенная на цель
     */
    double getTargetByPeriod(LocalDate begin, LocalDate end, Person person);

    /**
     * Получение списка записей о накоплении на цель за период
     * @param begin - начало периода
     * @param end - конец периода
     * @param person - пользователь программы
     * @return - List<GrossBook>
     */
    List<GrossBook> getListTargetByPeriod(LocalDate begin, LocalDate end, Person person);

    /**
     * Изменение записи
     * @param expenses - изменяемая запись
     * @return - результат операции true - успех/false - неудача
     */
    boolean editGrossBook(GrossBook grossBook);


}
