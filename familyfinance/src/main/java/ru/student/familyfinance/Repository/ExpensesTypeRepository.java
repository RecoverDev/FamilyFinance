package ru.student.familyfinance.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.student.familyfinance.Model.ExpensesType;

@Repository
public interface ExpensesTypeRepository extends CrudRepository<ExpensesType,Long>{

}
