package ru.student.familyfinance_desktop.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class TargetRepositoryImplementation implements Repository<Target> {
    private List<Target> collection = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void setCollection(List<Target> collection) {
        List<Target> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, this.collection);
    }

    @Override
    public List<Target> getCollection() {
        return this.collection;
    }

    @Override
    public Target getItemById(long id) {
        List<Target> result = this.collection.stream().filter(target -> target.getId() == id).toList();
        return !result.isEmpty() ? result.getFirst() :null;
    }

    @Override
    public boolean addItem(Target item) {
        if (item == null) {
            return false;
        }
        List<Target> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public boolean editItem(Target item) {
        Target result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        List<Target> oldValue = List.copyOf(this.collection);
        boolean isEqual = this.collection.set(index, item).equals(result);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return isEqual;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Target> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.removeIf(target -> target.getId() == id);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

}
