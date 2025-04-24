package ru.student.familyfinance.Service;

import java.util.List;

import org.springframework.data.util.Pair;
import ru.student.familyfinance.Model.Basket;
import ru.student.familyfinance.Model.Person;
import ru.student.familyfinance.Model.Shop;

/**
 * Сервис описывает действия со списками покупок пользователя
 */
public interface BasketService {

    /**
     * Добавление нового товара в список покупок
     * @param basket - новая запись
     * @return - добавленная запись
     */
    Basket addBasket(Basket basket);

    /**
     * Удаление записи о товаре
     * @param id - идентификатор записи
     * @return - результат операции true - успех/false - неудача
     */
    boolean removeBasket(long id);

    /**
     * Получение списка покупок пользователя
     * @param person - пользователь
     * @return - список покупок
     */
    List<Basket> getBasket(Person person);

    /**
     * Получение записи о покупке по идентификатору
     * @param id - идентификатор записи
     * @return - запись о покупке
     */
    Basket getBasketById(long id);

    /**
     * Редактирование записи о покупке
     * @param basket - редактируемая запись
     * @return - отредактированная запись
     */
    Basket editBasket(Basket basket);

    /**
     * Получение списка записей пользователя по магазину
     * @param person - пользователь
     * @param shop - магазин
     * @return - список записей
     */
    List<Basket> getBasketByShop(Person person, Shop shop);

    /**
     * Создание покупки по списку записей
     * @param person - пользователь
     * @param - список записей и стоимостей
     * @return - результат операции true - успех/false - неудача
     */
    boolean makePurchase(Person person, List<Pair<Basket, Double>> purchase);

}
