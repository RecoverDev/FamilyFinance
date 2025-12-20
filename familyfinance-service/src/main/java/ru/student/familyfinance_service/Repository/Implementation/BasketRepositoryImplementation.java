package ru.student.familyfinance_service.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_service.Model.Basket;
import ru.student.familyfinance_service.Repository.Repository;

@Component
public class BasketRepositoryImplementation implements Repository<Basket> {
    private List<Basket> collection  = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void setCollection(List<Basket> collection) {
        List<Basket> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, this.collection);
    }

    @Override
    public List<Basket> getCollection() {
        return this.collection;
    }

    @Override
    public Basket getItemById(long id) {
        List<Basket> result = this.collection.stream().filter(item -> item.getId() == id).toList();
        return !result.isEmpty() ? result.getFirst() : null;
    }

    @Override
    public boolean addItem(Basket item) {
        if (item == null) {
            return false;
        }
        List<Basket> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public boolean editItem(Basket item) {
        Basket result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        List<Basket> oldValue = List.copyOf(this.collection);
        boolean isEqual = this.collection.set(index, item).equals(result);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return isEqual;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Basket> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.removeIf(target -> target.getId() == id);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}
