package ru.student.familyfinance_desktop.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class PlanRepositoryImplementation implements Repository<Plan> {
    private List<Plan> collection = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void setCollection(List<Plan> collection) {
        List<Plan> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, this.collection);
    }

    @Override
    public List<Plan> getCollection() {
        return collection;
    }

    @Override
    public Plan getItemById(long id) {
        List<Plan> result = this.collection.stream().filter(plan -> plan.getId() == id).toList();
        return !result.isEmpty() ? result.getFirst() : null; 
    }

    @Override
    public boolean addItem(Plan item) {
        List<Plan> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public boolean editItem(Plan item) {
        Plan plan = getItemById(item.getId());
        int index = this.collection.size();
        if (plan != null) {
            index = this.collection.indexOf(plan);
        }
        List<Plan> oldValue = List.copyOf(this.collection);
        boolean isEqual =  this.collection.set(index, item).equals(plan);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return isEqual;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Plan> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.removeIf(plan -> plan.getId() == id);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }
}
