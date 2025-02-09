package ru.student.familyfinance_desktop.Service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Repository.IncomeRepository;
import ru.student.familyfinance_desktop.RestController.IncomeRestController;
import ru.student.familyfinance_desktop.Service.IncomeService;

@Service
@RequiredArgsConstructor
public class IncomeServiceImplementation implements IncomeService{
    private final IncomeRepository repository;
    private final IncomeRestController controller;

    @Override
    public void setIncomes() {
        List<Income> list = controller.getIncomes();
        repository.setIncomes(list);
    }

    @Override
    public List<Income> getIncomes() {
        return repository.getIncomes();
    }

    @Override
    public Income getIncomeById(long id) {
        return repository.getIncomeById(id);
    }

    @Override
    public boolean deleteIncomeById(long id) {
        if (controller.deleteIncomeById(id)) {
            return repository.deleteIncomeById(id);
        }
        return false;
    }

    @Override
    public boolean addIncome(Income income) {
        Income result = controller.addIncome(income);
        if (result != null) {
            return repository.addIncome(income);
        }
        return false;
    }

    @Override
    public boolean editIncome(Income income) {
        Income result = controller.editIncome(income);
        if (result != null) {
            return repository.updateIncome(income);
        }
        return false;
    }

}
