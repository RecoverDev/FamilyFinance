package ru.student.familyfinance.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long>{

    List<Shop> findByPerson(Person person);

}
