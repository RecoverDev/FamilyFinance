package ru.student.familyfinance.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Person;

@Repository
public interface ExpensesRepository extends CrudRepository<Expenses,Long>{
    List<Expenses> findByPerson(Person person);
}
