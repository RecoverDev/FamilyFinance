package ru.student.familyfinance_desktop.Service;

import java.beans.PropertyChangeListener;
import java.util.List;

//import javafx.util.Pair;
import org.springframework.data.util.Pair;
import ru.student.familyfinance_desktop.Model.Basket;

/**
 * Сервис поработе со списком товаров для покупок
 */
public interface BasketService {

    /**
     * Заполнение репозитория
     */
    void setBaskets();

    /**
     * Получить полный список товаров для покупок
     * @return - список покупок
     */
    List<Basket> getBaskets();

    /**
     * Получение товара по идентификатору
     * @param id - идентификатор товара
     * @return - товар
     */
    Basket getBasketById(long id);

    /**
     * Получение списка товаров по идентификатору магазина
     * @param idShop - идентификатор магазина
     * @return - список товаров
     */
    List<Basket> getBasketsByShop(long idShop);

    /**
     * Добавление товара в список
     * @param basket - добавляемый товар
     * @return - добавленный товар
     */
    Basket addBasket(Basket basket);

    /**
     * Удаление товара по идентификатору
     * @param id - идентификатор удаляемого товара
     * @return - true - успех/false - неудача
     */
    boolean removeBasketById(long id);

    /**
     * Редактирование товара
     * @param basket - редактируемый товар
     * @return - отредактированный товар
     */
    Basket editBasket(Basket basket);

    /**
     * Создание покупки по списку товаров
     * @param baskets - список товаров с суммами
     * @return - true - успех/false - неудача
     */
    boolean makePurchase(List<Pair<Basket,Double>> baskets);

    /**
     * Установить слушателя на изменение репозотория ShopRepository
     * @param listener
     */
    void setRepositoryListener(PropertyChangeListener listener);
}
