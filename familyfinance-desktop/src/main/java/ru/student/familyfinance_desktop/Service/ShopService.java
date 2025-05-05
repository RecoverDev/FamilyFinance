package ru.student.familyfinance_desktop.Service;

import java.beans.PropertyChangeListener;
import java.util.List;

import ru.student.familyfinance_desktop.Model.Shop;

/**
 * Сервис по работе сос списком магазинов пользователя
 */
public interface ShopService {

    /**
     * Заполнение репозитория
     */
    void setShops();

    /**
     * Получение полного списка магазинов пользователя
     * @return - список магазинов
     */
    List<Shop> getShops();

    /**
     * Получение магазина по идентификатору
     * @param id - идетнтификатор магазина
     * @return - магазин Shop
     */
    Shop getShopById(long id);

    /**
     * Добавление нового магазина
     * @param shop - добавляемый магазин
     * @return - true - успех/false - неудача
     */
    boolean addShop(Shop shop);

    /**
     * Редактирование магазина
     * @param shop - редактируемый магазин
     * @return - true - успех/false - неудача
     */
    boolean editShop(Shop shop);

    /**
     * Удаление магазина по идентификатору
     * @param id - идентификатор удаляемого магазина
     * @return - true - успех/false - неудача
     */
    boolean deleteShopById(long id);

    /**
     * Установить слушателя на изменение репозотория ShopRepository
     * @param listener
     */
    void setRepositoryListener(PropertyChangeListener listener);

}
