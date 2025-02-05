package ru.student.familyfinance_desktop.Service.Implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.ExpensesType;
import ru.student.familyfinance_desktop.Repository.ExpensesTypeRepository;
import ru.student.familyfinance_desktop.RestController.RestExpensesTypeController;
import ru.student.familyfinance_desktop.Service.ExpensesTypeService;

@Component
@RequiredArgsConstructor
public class ExpensesTypeServiceImplementation implements ExpensesTypeService {
    private final ExpensesTypeRepository repository;
    private final RestExpensesTypeController controller;

    @Override
    public void setExpensesTypes() {
        List<ExpensesType> list = controller.getExpensesTypes();
        repository.setExpensesType(list);
    }

    @Override
    public List<ExpensesType> getExpensesTypes() {
        return repository.getExpensesType();
    }

    @Override
    public ExpensesType getExpensesTypeById(long id) {
        return repository.getExpensesTypeById(id);
    }

    @Override
    public boolean addExpensesType(ExpensesType expensesType) {
        ExpensesType result = controller.addExpensesType(expensesType);
        if (result != null) {
            return repository.addExpensesType(expensesType);
        }
        return false;
    }

    @Override
    public boolean deleteExpensesTypeById(long id) {
        boolean result = controller.deleteExpensesTypeById(id);
        if (result) {
            return repository.deleteExpensesTypeById(id);
        }
        return false;
    }

    @Override
    public boolean editExpensesType(ExpensesType expensesType) {
        ExpensesType result = controller.editExpensesType(expensesType);
        if (result != null) {
            return repository.editExpensesType(expensesType);
        }
        return false;
    }

}
