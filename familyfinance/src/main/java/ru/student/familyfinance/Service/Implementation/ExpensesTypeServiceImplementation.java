package ru.student.familyfinance.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance.Model.ExpensesType;
import ru.student.familyfinance.Repository.ExpensesTypeRepository;
import ru.student.familyfinance.Service.ExpensesTypeService;

@Service
@RequiredArgsConstructor
public class ExpensesTypeServiceImplementation implements ExpensesTypeService {
    private final ExpensesTypeRepository repository;
    
    @Override
    public boolean addExpensesType(ExpensesType expensesType) {
        if (expensesType == null) {
            return false;
        }
        ExpensesType result = repository.save(expensesType);
        return expensesType.equals(result);
    }

    @Override
    public boolean removeExpensesType(long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    @Override
    public List<ExpensesType> getExpensesType() {
        return (List<ExpensesType>)repository.findAll();
    }

    @Override
    public ExpensesType getExpensesTypeById(long id) {
        Optional<ExpensesType> result =  repository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public boolean editExpensesType(ExpensesType expensesType) {
        if (expensesType == null) {
            return false;
        }
        ExpensesType result = repository.save(expensesType);
        return expensesType.equals(result);
    }

}
