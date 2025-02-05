package ru.student.familyfinance_desktop.Model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс описывает типы категорий расходов
 */
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesType {

    long id;

    /**
     * Наименование типа категории расходов
     */
    String name;

    @Override
    public String toString() {
        return this.getName();
    }

}
