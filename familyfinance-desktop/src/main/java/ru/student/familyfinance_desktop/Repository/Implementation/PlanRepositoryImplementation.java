package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.student.familyfinance_desktop.Model.Plan;
import ru.student.familyfinance_desktop.Repository.Repository;

@Component
public class PlanRepositoryImplementation implements Repository<Plan> {
    private List<Plan> collection = new ArrayList<>();

    @Override
    public void setCollection(List<Plan> collection) {
        this.collection.removeAll(this.collection);
        this.collection.addAll(collection);
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
        return this.collection.add(item);
    }

    @Override
    public boolean editItem(Plan item) {
        Plan plan = getItemById(item.getId());
        int index = this.collection.size();
        if (plan != null) {
            index = this.collection.indexOf(plan);
        }
        return this.collection.set(index, item).equals(plan);
    }

    @Override
    public boolean deleteItemById(long id) {
        return this.collection.removeIf(plan -> plan.getId() == id);
    }

}
