package ru.student.familyfinance_service.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_service.Model.GrossBook;
import ru.student.familyfinance_service.Repository.Repository;

@Component
public class GrossBookRepositoryImplementation implements Repository<GrossBook> {
    private List<GrossBook> collection = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void setCollection(List<GrossBook> collection) {
        List<GrossBook> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, collection);
    }

    @Override
    public List<GrossBook> getCollection() {
        return this.collection;
    }

    @Override
    public GrossBook getItemById(long id) {
        List<GrossBook> result = this.collection.stream().filter(grossBook -> grossBook.getId() == id).toList();
        return !result.isEmpty() ? result.getFirst() : null;
    }

    @Override
    public boolean addItem(GrossBook item) {
        if (item == null) {
            return false;
        }
        List<GrossBook> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, collection);
        return result;
    }

    @Override
    public boolean editItem(GrossBook item) {
        GrossBook result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        List<GrossBook> oldValue = List.copyOf(this.collection);
        boolean isEquals = this.collection.set(index, item).equals(result);
        pcs.firePropertyChange("collection", oldValue, collection);
        return isEquals;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<GrossBook> oldValue = List.copyOf(this.collection);
        boolean result =  this.collection.removeIf(grossBook -> grossBook.getId() == id);
        pcs.firePropertyChange("collection", oldValue, collection);
        return result;
    }

}
