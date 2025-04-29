package ru.student.familyfinance_desktop.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Shop;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class ShopRepositoryImplementation implements Repository<Shop> {
    private List<Shop> collection  = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void setCollection(List<Shop> collection) {
        List<Shop> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, this.collection);
    }

    @Override
    public List<Shop> getCollection() {
        return this.collection;
    }

    @Override
    public Shop getItemById(long id) {
        List<Shop> result = this.collection.stream().filter(item -> item.getId() == id).toList();
        return !result.isEmpty() ? result.getFirst() : null;
    }

    @Override
    public boolean addItem(Shop item) {
        if (item == null) {
            return false;
        }
        List<Shop> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public boolean editItem(Shop item) {
        Shop result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        List<Shop> oldValue = List.copyOf(this.collection);
        boolean isEqual = this.collection.set(index, item).equals(result);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return isEqual;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Shop> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.removeIf(target -> target.getId() == id);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}
