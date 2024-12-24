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
 * Класс описывает цели семьи для сбережения средств
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Target implements Subject{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Пользователь, которому принадлежит запись
     */
    @OneToOne
     Person person;
    /**
     * Наименование цели
     */
    String name;
    /**
     * Сумма, необходимая для достижения цели
     */
    double summ;


}
