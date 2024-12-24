package ru.student.familyfinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Plan;

@Repository
public interface PlanRepository extends CrudRepository<Plan, Long>{

    List<Plan> findByPersonAndDateOfOperation(Person person, LocalDate dateOfOperation);

}
