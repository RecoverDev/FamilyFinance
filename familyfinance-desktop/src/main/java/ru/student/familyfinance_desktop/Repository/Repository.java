package ru.student.familyfinance_desktop.Repository;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Хранилище моделей, реализует CRUD операции
 */
public interface Repository<T> {

    /**
     * Добавляет модели в список хранилища
     * @param collection - добавляемый список
     */
    void setCollection(List<T> collection);

    /**
     * Возвращает весь список моделей
     * @return - List<T> список моделей
     */
    List<T> getCollection();

    /**
     * Возвращает модель по ID
     * @param id - ID модели 
     * @return - найденная модель или NULL
     */
    T getItemById(long id);

    /**
     * Добавляет новую модель в список
     * @param income - добавляемая модель
     * @return - true - успех/false - неудача
     */
    boolean addItem(T item);

    /**
     * Обновление (изменение) модели
     * @param income - измененная модель
     * @return - true - успех/false - неудача
     */
    boolean editItem(T item);

    /**
     * Удаление модели по ID
     * @param id - ID удаляемой модели
     * @return - true - успех/false - неудача
     */
    boolean deleteItemById(long id);

    /**
     * Установить слушателя на изменение репозотория
     * @param listener
     */
    void addListener(PropertyChangeListener listener);

}
