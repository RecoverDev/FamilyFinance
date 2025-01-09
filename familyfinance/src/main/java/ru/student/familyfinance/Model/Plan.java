package ru.student.familyfinance.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс описывает план семьи в разрезе доходов и расходов на месяц
 */
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Первое число месяца планирования
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
     * Планируемая сумма
     */
    double summ;
}
