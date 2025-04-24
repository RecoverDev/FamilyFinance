package ru.student.familyfinance.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    List<Product> findByPerson(Person person);
}
