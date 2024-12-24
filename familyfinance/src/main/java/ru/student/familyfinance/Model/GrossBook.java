package ru.student.familyfinance.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс описывает факические доходы и расходы по дням
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GrossBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Дата совершения операции
     */
    LocalDate dateOfOperation;
    /**
     * Пользователь, которому принадлежит запись
     */
    @OneToOne
    Person person;
    /**
     * Категория дохода
     */
    @OneToOne
    Income income;
    /**
     * Категория расхода
     */
    @OneToOne
     Expenses expenses;
    /**
     * Цель накопления
     */
    @OneToOne
     Target target;
    /**
     * Сумма операции
     */
    double summ;

}
