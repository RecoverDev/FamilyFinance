package ru.student.familyfinance.Model;

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
 * Класс описывает категории расодов
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expenses implements Subject{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Пользователь, которому принадлежит категория дохода
     */
    @OneToOne
    Person person;
    
    /**
     * Наименование категории расходов
     */
    String name;

    /**
     * Тип категории расходов
     */
    @OneToOne
    ExpensesType expensesType;

}
