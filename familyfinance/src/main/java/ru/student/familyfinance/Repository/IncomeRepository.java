package ru.student.familyfinance.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Long>{
    List<Income> findByPerson(Person person);

}
