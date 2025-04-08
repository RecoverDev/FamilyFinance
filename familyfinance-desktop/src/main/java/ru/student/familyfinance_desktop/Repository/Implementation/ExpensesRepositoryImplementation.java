package ru.student.familyfinance_desktop.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Expenses;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class ExpensesRepositoryImplementation implements Repository<Expenses> {
    private List<Expenses> collection = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void setCollection(List<Expenses> collection) {
        List<Expenses> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, collection);
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
        List<Expenses> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, collection);
        return result;
    }

    @Override
    public boolean editItem(Expenses item) {
        Expenses result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        List<Expenses> oldValue = List.copyOf(this.collection);
        boolean isEqual = this.collection.set(index, item).equals(result);
        pcs.firePropertyChange("collection", oldValue, collection);
        return isEqual;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Expenses> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.removeIf(item -> item.getId() == id);
        pcs.firePropertyChange("collection", oldValue, collection);
        return result;
    }

}
