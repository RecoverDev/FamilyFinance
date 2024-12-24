package ru.student.familyfinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.GrossBook;
import ru.student.familyfinance.Model.Person;

@Repository
public interface GrossBookRepository extends CrudRepository<GrossBook, Long>{
    
    List<GrossBook> findByPerson(Person person);
    List<GrossBook> findByPersonAndDateOfOperationBetween(Person person, LocalDate begin, LocalDate end);

}
