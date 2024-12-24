package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.Income;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Repository.IncomeRepository;
import ru.student.familyfinance.Service.IncomeService;

@Service
@RequiredArgsConstructor
public class IncomeServiceImplementation implements IncomeService {
    private final IncomeRepository repository;

    @Override
    public boolean addIncome(Income income) {
        if (income == null) {
            return false;
        }
        Income result = repository.save(income);
        return income.equals(result);
    }

    @Override
    public boolean removeIncome(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<Income> getIncomes(Person person) {
        return (List<Income>)repository.findByPerson(person);
    }

    @Override
    public Income getIncomeById(long id) {
        Optional<Income> result = repository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public boolean editIncome(Income income) {
        if (income == null) {
            return false;
        }
        Income result = repository.save(income);
        return income.equals(result);
    }

}
