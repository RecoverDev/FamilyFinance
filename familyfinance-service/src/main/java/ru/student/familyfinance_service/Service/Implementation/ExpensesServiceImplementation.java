package ru.student.familyfinance_service.Service.Implementation;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_service.Model.Expenses;
import ru.student.familyfinance_service.Repository.Repository;
import ru.student.familyfinance_service.RestController.ExpensesRestController;
import ru.student.familyfinance_service.Service.ExpensesService;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImplementation implements ExpensesService {
    private final Repository<Expenses> repository;
    private final ExpensesRestController controller;

    @Override
    public void setExpenses() {
        List<Expenses> list = controller.getExpenses();
        repository.setCollection(list);
    }

    @Override
    public List<Expenses> getExpenses() {
        return repository.getCollection();
    }

    @Override
    public Expenses getExpensesById(long id) {
        return repository.getItemById(id);
    }

    @Override
    public boolean addExpenses(Expenses expenses) {
        Expenses result = controller.addExpenses(expenses);
        if (result != null) {
            return repository.addItem(result);
        }
        return false;
    }

    @Override
    public boolean editExpenses(Expenses expenses) {
        if (expenses == null) {
            return false;
        }
        Expenses result = controller.editExpenses(expenses);
        if (result == null) {
            return false;
        }
        return repository.editItem(result);
    }

    @Override
    public boolean deleteExpensesById(long id) {
        if (controller.deleteExpensesById(id)) {
            return repository.deleteItemById(id);
        }
        return false;
    }

    @Override
    public void setRepositoryListener(PropertyChangeListener listener) {
        repository.addListener(listener);
    }
}
