package ru.student.familyfinance_desktop.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import ru.student.familyfinance_desktop.Model.Target;
import ru.student.familyfinance_desktop.Repository.Repository;

public class TargetRepositoryImplementation implements Repository<Target> {
    private List<Target> collection = new ArrayList<>();

    @Override
    public void setCollection(List<Target> collection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCollection'");
    }

    @Override
    public List<Target> getCollection() {
        return this.collection;
    }

    @Override
    public Target getItemById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getItemById'");
    }

    @Override
    public boolean addItem(Target item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addItem'");
    }

    @Override
    public boolean editItem(Target item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editItem'");
    }

    @Override
    public boolean deleteItemById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteItemById'");
    }

}
