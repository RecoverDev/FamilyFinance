package ru.student.familyfinance.Model;

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
 * Корзина пользователя с планируемыми для покупки товарами
 */
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Полльзователь, которому принадлежит корзина
     */
    @OneToOne
    Person person;

    /**
     * Магазин, в котором планируется приобрести товар
     */
    @OneToOne
    Shop shop;

    /**
     * Товар, который планируется приобрести
     */
    @OneToOne
    Product product;

}
