package ru.student.familyfinance_desktop.Service;

import java.time.LocalDate;
import java.util.List;

import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.Target;

/**
 * Сервис по работе с таблицей записей пользователя о доходах/расходах 
 */
public interface GrossBookService {

    /**
     * Заполнение репозитория
     * @param begin - начальная дата периода выборки записей
     * @param end - конечная двта периода выборки записей
     */
    void setGrossBooks(LocalDate begin, LocalDate end);
    
    /**
     * Получение полного списка записей пользователя о доходах/расходах 
     * @return - список записей пользователя о доходах/расходах List<GrossBook>
     */
    List<GrossBook> getGrossBooks();

    /**
     * Получение записи по ID
     * @param id - идентификатор записи
     * @return - запись
     */
    GrossBook getGrossBookById(long id);

    /**
     * Добавление новой записи о доходах/расходах
     * @param grossBook - новая запись
     * @return - true - успех/false - неудача
     */
    boolean addGrossBook(GrossBook grossBook);

    /**
     * Изменение записи о доходах/расходах
     * @param grossBook - измененная запись
     * @return - true - успех/false - неудача
     */
    boolean editGrossBook(GrossBook grossBook);

    /**
     * Удалание записи о доходах/расходах по ID
     * @param id - идентификатор записи
     * @return - true - успех/false - неудача
     */
    boolean deleteGrossBookById(long id);

    /**
     * Получение всех записей пользователя о целях указанных в списке
     * @param targets - список целей
     * @return - список записей List<GrossBook>
     */
    List<GrossBook> getGrossBookByScroll(List<Target> targets);

}
