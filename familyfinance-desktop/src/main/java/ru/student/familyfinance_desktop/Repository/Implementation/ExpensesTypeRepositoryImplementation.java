package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ru.student.familyfinance_desktop.Model.ExpensesType;
import ru.student.familyfinance_desktop.Repository.ExpensesTypeRepository;

@Repository
public class ExpensesTypeRepositoryImplementation implements ExpensesTypeRepository {
    private List<ExpensesType> expensesTypes = new ArrayList<>();

    @Override
    public void setExpensesType(List<ExpensesType> listExpensesType) {
        this.expensesTypes.removeAll(this.expensesTypes);
        this.expensesTypes.addAll(listExpensesType);
    }

    @Override
    public List<ExpensesType> getExpensesType() {
        return this.expensesTypes;
    }

    @Override
    public ExpensesType getExpensesTypeById(long id) {
        List<ExpensesType> result = this.expensesTypes.stream().filter(expensesType -> expensesType.getId() == id).toList();
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public boolean addExpensesType(ExpensesType expensesType) {
        return this.expensesTypes.add(expensesType);
    }

    @Override
    public boolean editExpensesType(ExpensesType expensesType) {
        ExpensesType result = getExpensesTypeById(expensesType.getId());
        int index = this.expensesTypes.size();
        if (result != null) {
            index = this.expensesTypes.indexOf(result);
        }
        return this.expensesTypes.set(index, expensesType).equals(result);
    }

    @Override
    public boolean deleteExpensesTypeById(long id) {
        return this.expensesTypes.removeIf(expensesType -> expensesType.getId() == id);
    }

}
