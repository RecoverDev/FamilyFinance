package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class TargetRepositoryImplementation implements Repository<Target> {
    private List<Target> collection = new ArrayList<>();

    @Override
    public void setCollection(List<Target> collection) {
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
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
        return this.collection.add(item);
    }

    @Override
    public boolean editItem(Target item) {
        Target result = getItemById(item.getId());
        int index = this.collection.size();
        if (result != null) {
            index = this.collection.indexOf(result);
        }
        return this.collection.set(index, item).equals(result);
    }

    @Override
    public boolean deleteItemById(long id) {
        return this.collection.removeIf(target -> target.getId() == id);
    }

}
