package ru.student.familyfinance.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Target;

@Repository
public interface TargetRepository extends CrudRepository<Target,Long> {

    List<Target> findByPerson(Person person);

}
