package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ru.student.familyfinance_desktop.Model.Income;
import ru.student.familyfinance_desktop.Repository.IncomeRepository;

@Repository
public class IncomeRepositoryImplementation implements IncomeRepository{
    private List<Income> incomes = new ArrayList<>();


    @Override
    public void setIncomes(List<Income> incomes) {
        this.incomes.removeAll(this.incomes);
        this.incomes.addAll(incomes);
    }

    @Override
    public List<Income> getIncomes() {
        return incomes;
    }

    @Override
    public Income getIncomeById(long id) {
        List<Income> result = incomes.stream().filter(income -> income.getId() == id).toList();
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public boolean addIncome(Income income) {
        return incomes.add(income);
    }

    @Override
    public boolean deleteIncomeById(long id) {
        return incomes.removeIf(income -> income.getId() == id);
    }

    @Override
    public boolean updateIncome(Income income) {
        Income result = getIncomeById(income.getId());
        int index = incomes.size();
        if (result != null) {
            index = incomes.indexOf(result);
        }
        return incomes.set(index, income).equals(result);
    }

}
