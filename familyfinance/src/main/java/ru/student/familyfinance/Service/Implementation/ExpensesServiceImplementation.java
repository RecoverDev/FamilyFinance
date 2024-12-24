package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Expenses;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.ExpensesRepository;
import ru.student.familyfinance.Service.ExpensesService;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImplementation implements ExpensesService {
    private final ExpensesRepository repository;

    @Override
    public boolean addExpenses(Expenses expenses) {
        if (expenses == null) {
            return false;
        }
        Expenses result = repository.save(expenses);
        return expenses.equals(result);
    }

    @Override
    public boolean removeExpenses(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Expenses> getExpenses(Person person) {
        return (List<Expenses>)repository.findByPerson(person);
    }

    @Override
    public Expenses getExpensesById(long id) {
        Optional<Expenses> result = repository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public boolean editExpenses(Expenses expenses) {
        if (expenses == null) {
            return false;
        }
        Expenses result = repository.save(expenses);
        return expenses.equals(result);
    }

}
