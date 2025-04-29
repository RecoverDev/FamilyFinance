package ru.student.familyfinance_desktop.Repository.Implementation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Product;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class ProductRepositoryImplementation implements Repository<Product> {
    private List<Product> collection  = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void setCollection(List<Product> collection) {
        List<Product> oldValue = List.copyOf(this.collection);
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
        pcs.firePropertyChange("collection", oldValue, this.collection);
    }

    @Override
    public List<Product> getCollection() {
        return this.collection;
    }

    @Override
    public Product getItemById(long id) {
        List<Product> result = this.collection.stream().filter(item -> item.getId() == id).toList();
        return !result.isEmpty() ? result.getFirst() : null;
    }

    @Override
    public boolean addItem(Product item) {
        if (item == null) {
            return false;
        }
        List<Product> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.add(item);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public boolean editItem(Product item) {
        Product result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        List<Product> oldValue = List.copyOf(this.collection);
        boolean isEqual = this.collection.set(index, item).equals(result);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return isEqual;
    }

    @Override
    public boolean deleteItemById(long id) {
        List<Product> oldValue = List.copyOf(this.collection);
        boolean result = this.collection.removeIf(target -> target.getId() == id);
        pcs.firePropertyChange("collection", oldValue, this.collection);
        return result;
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}
