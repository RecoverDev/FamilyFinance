package ru.student.familyfinance.Service;

import java.util.List;

import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;

/**
 * Сервис описывает действия со списком магазимов
 */
public interface ShopService {

    /**
     * Добавление нового магазина в список
     * @param shop - новый магазин
     * @return - добавленный магазин
     */
    Shop addShop(Shop shop);

    /**
     * Удаление магазина из списка по идентификатору
     * @param id - идентификатор
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeShop(long id);

    /**
     * Получение списка магазинов
     * @param person - пользователь
     * @return - список магазинов
     */
    List<Shop> getShops(Person person);

    /**
     * Получение записи магазина по идентификатору
     * @param id - идентификатор
     * @return - магазин
     */
    Shop getShopById(Long id);

    /**
     * Редактирование магазина
     * @param shop - редактируемый магазин
     * @return - отредактированный магазин
     */
    Shop editShop(Shop shop);
}
