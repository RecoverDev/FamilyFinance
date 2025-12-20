package ru.student.familyfinance_service.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


import ru.student.familyfinance_service.Model.Income;
import ru.student.familyfinance_service.Repository.Repository;


@org.springframework.stereotype.Repository
public class IncomeRepositoryImplementation implements Repository<Income> {
    private List<Income> incomes = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }


    @Override
    public void setCollection(List<Income> incomes) {
        List<Income> oldValue = List.copyOf(this.incomes);
        this.incomes.removeAll(this.incomes);
        this.incomes.addAll(incomes);
        pcs.firePropertyChange("incomes", oldValue, incomes);
    }

    @Override
    public List<Income> getCollection() {
        return incomes;
    }

    @Override
    public Income getItemById(long id) {
        List<Income> result = incomes.stream().filter(income -> income.getId() == id).toList();
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public boolean addItem(Income income) {
        if (income == null) {
            return false;
        }
        List<Income> oldValue = List.copyOf(this.incomes);
        boolean result = incomes.add(income);
        pcs.firePropertyChange("incomes", oldValue, incomes);
        return result;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Income> oldValue = List.copyOf(this.incomes);
        boolean result =  incomes.removeIf(income -> income.getId() == id);
        pcs.firePropertyChange("incomes", oldValue, incomes);
        return result;
    }

    @Override
    public boolean editItem(Income income) {
        Income result = getItemById(income.getId());
        int index = incomes.size();
        if (result != null) {
            index = incomes.indexOf(result);
        }
        List<Income> oldValue = List.copyOf(this.incomes);
        boolean isEqual = incomes.set(index, income).equals(result);
        pcs.firePropertyChange("incomes", oldValue, incomes);
        return isEqual;
    }

}
