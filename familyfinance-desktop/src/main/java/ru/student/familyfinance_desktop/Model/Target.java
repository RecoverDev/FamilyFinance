package ru.student.familyfinance_desktop.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Цели пользователя
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Target {

    long id;

    /**
     * Идентификатор пользователя, которому принадлежит цель
     */
    long person_id;

    /**
     * Наименование цели
     */
    String name;

    /**
     * Сумма, необходимая для достижения цели
     */
    double summ;

    /**
     * Дата установки цели
     */
    LocalDate settingDate = LocalDate.now();
}
