package ru.student.familyfinance.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long>{
    Person findByUsername(String username);
}
