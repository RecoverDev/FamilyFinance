package ru.student.familyfinance_desktop.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Описание плана пользователя
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    
    /**
     * Идентификатор записи плана пользователя
     */
    long id;

    /**
     * Период плана (первый день месяца)
     */
    LocalDate dateOfOperation;

    /**
     * Идентификатор пользователя, владельца плана
     */
    long person_id;

    /**
     * Идентификатор цели пользователя
     */
    long target_id;

    /**
     * Идентификатор вида расходов
     */
    long expenses_id;

    /**
     * Идентификатор вида доходов
     */
    long income_id;

    /**
     * Сумма пункта плана
     */
    double summ;

}
