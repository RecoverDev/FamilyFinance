package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class ExpensesRepositoryImplementation implements Repository<Expenses> {
    private List<Expenses> collection = new ArrayList<>();

    @Override
    public void setCollection(List<Expenses> collection) {
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
    }

    @Override
    public List<Expenses> getCollection() {
        return this.collection;
    }

    @Override
    public Expenses getItemById(long id) {
        List<Expenses> result = this.collection.stream().filter(item -> item.getId() == id).toList();
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public boolean addItem(Expenses item) {
        if (item == null) {
            return false;
        }
        return this.collection.add(item);
    }

    @Override
    public boolean editItem(Expenses item) {
        Expenses result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        return this.collection.set(index, item).equals(result);
    }

    @Override
    public boolean deleteItemById(long id) {
        return this.collection.removeIf(item -> item.getId() == id);
    }

}
