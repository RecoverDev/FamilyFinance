package ru.student.familyfinance.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {
    List<Basket> findByPerson(Person person);
    List<Basket> findByPersonAndShop(Person person, Shop shop);
}
