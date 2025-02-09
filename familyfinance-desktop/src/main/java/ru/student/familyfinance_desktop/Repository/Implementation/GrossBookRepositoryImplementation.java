package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.GrossBook;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class GrossBookRepositoryImplementation implements Repository<GrossBook> {
    private List<GrossBook> collection = new ArrayList<>();

    @Override
    public void setCollection(List<GrossBook> collection) {
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
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
        return this.collection.add(item);
    }

    @Override
    public boolean editItem(GrossBook item) {
        GrossBook result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        return this.collection.set(index, item).equals(result);
    }

    @Override
    public boolean deleteItemById(long id) {
        return this.collection.removeIf(grossBook -> grossBook.getId() == id);
    }

}
