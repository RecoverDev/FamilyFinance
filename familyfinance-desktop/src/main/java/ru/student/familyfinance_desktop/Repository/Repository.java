package ru.student.familyfinance_desktop.Repository;

import java.util.List;

public interface Repository<T> {

    void setCollection(List<T> collection);

    List<T> getCollection();

    T getItemById(long id);

    boolean addItem(T item);

    boolean editItem(T item);

    boolean deleteItemById(long id);

}
