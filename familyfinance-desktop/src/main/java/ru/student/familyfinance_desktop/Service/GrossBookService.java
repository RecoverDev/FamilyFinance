package ru.student.familyfinance_desktop.Service;

import java.util.List;

import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Model.Target;

/**
 * Сервис по работе с таблицей записей пользователя о доходах/расходах 
 */
public interface GrossBookService {

    
    /**
     * Получение полного списка записей пользователя о доходах/расходах 
     * @return - список записей пользователя о доходах/расходах List<GrossBook>
     */
    List<GrossBook> getGrossBooks();

    /**
     * Получение всех записей пользователя о целях указанных в списке
     * @param targets - список целей
     * @return - список записей List<GrossBook>
     */
    List<GrossBook> getGrossBookByScroll(List<Target> targets);

}
